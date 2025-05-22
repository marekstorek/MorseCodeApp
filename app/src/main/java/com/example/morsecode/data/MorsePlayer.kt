package com.example.morsecode.data

import android.app.Application
import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.Normalizer

class MorsePlayer(application: Application){
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val vibrator = application.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    private val cameraManager = application.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private val cameraId: String? by lazy {
        cameraManager.cameraIdList.firstOrNull {
            cameraManager.getCameraCharacteristics(it)
                .get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
        }
    }
    private val toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 80)

    private val pauseState = MutableStateFlow(false)

    var speedLevel = mutableStateOf(1f) // 0.25 - 4.00

    private val baseDuration = 120L
    private val duration: Long
        get() = (baseDuration * speedLevel.value).toLong()
    val status = mutableStateOf(Status.STOPPED)
    private var job: Job? = null

    val vibratingOn = mutableStateOf(false)
    val flashingOn = mutableStateOf(false)
    val soundOn = mutableStateOf(false)

    private val translationBuilder = StringBuilder()
    val translatedText = mutableStateOf("")
    val currentLetter = mutableStateOf<Char>(' ')

    enum class Status {
        PLAYING, PAUSED, STOPPED
    }

    private fun play(input: String){
        val text = input.normalize()
        clear()
        pauseState.value = false
        status.value = Status.PLAYING
        job = scope.launch {
            for (char in text){
                if (!chars.containsKey(char)){
                    if (char == ' '){
                        waitSpace()
                    } else if (char == '.'){
                        waitSentence()
                    }
                    continue
                } else {
                    currentLetter.value = char
                }
                val code = chars[char]
                for (symbol in code!!){
                    pauseState.filter { paused-> !paused }.first()
                    if (!isActive) break
                    if (symbol == '.'){
                        playDot()
                    } else if (symbol == '-'){
                        playDash()
                    }
                    waitBetweenSymbols()
                }
                waitBetweenLetters()
            }
            toggleStop()
            delay(500)
        }
    }


    private fun stop(){
        currentLetter.value = ' ';
        status.value = Status.STOPPED
        job?.cancel()
        closeFlash()
    }

    private fun pause(){
        status.value = Status.PAUSED
        pauseState.value = true
    }

    private fun resume(){
        status.value = Status.PLAYING
        pauseState.value = false
    }

    fun toggleFlash(){
        flashingOn.value = !flashingOn.value
    }

    fun toggleVibration(){
        vibratingOn.value = !vibratingOn.value
    }

    fun toggleSound(){
        soundOn.value = !soundOn.value
    }

    fun togglePlay(input: String){
        when (status.value) {
            Status.PLAYING -> pause()
            Status.PAUSED -> resume()
            Status.STOPPED -> play(input)
        }
    }

    fun toggleStop(){
        Log.i("STATUS", "stopped ->togglestop: Stopped")
        stop()
    }

    private fun String.normalize(): String{
        val regexUnAccent = "\\p{InCombiningDiacriticalMarks}+".toRegex()
        val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
        return ((regexUnAccent.replace(temp, "")).lowercase())
    }

    private suspend fun playDot(){
        append("•")
        openFlash()
        vibrate(duration)
        playSound(duration)
        delay(duration)
        closeFlash()
    }

    private suspend fun playDash(){
        append("‒")
        openFlash()
        vibrate(duration * 3)
        playSound(duration * 3)
        delay(duration * 3)
        closeFlash()
    }

    private suspend fun waitBetweenSymbols(){
        delay(duration)
    }

    private suspend fun waitBetweenLetters(){
        append(" ")
        delay(duration * 2)
    }

    private suspend fun waitSpace(){
        currentLetter.value = ' ';
        append(" / ")
        delay(duration * 3)
    }

    private suspend fun waitSentence(){
        currentLetter.value = '.'
        append(" // ")
        delay(duration * 7)
    }

    private fun openFlash(){
        if (!flashingOn.value)
            return
        cameraId?.let { cameraManager.setTorchMode(it, true) }
    }
    private fun closeFlash(){
        cameraId?.let { cameraManager.setTorchMode(it, false) }
    }

    private fun vibrate(length: Long){
        if(!vibratingOn.value)
            return
        vibrator.vibrate(
            VibrationEffect.createOneShot(
                length,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }

    private fun playSound(length: Long) {
        if (!soundOn.value)
            return
        toneGen.startTone(ToneGenerator.TONE_CDMA_DIAL_TONE_LITE, length.toInt())
    }

    private fun append(string: String){
        translationBuilder.append(string)
        translatedText.value = translationBuilder.toString()
    }

    private fun clear(){
        translationBuilder.clear()
        translatedText.value = translationBuilder.toString()
    }

    fun release() {
        stop()
        job?.cancel()
        toneGen.release()
        closeFlash()
    }

    private val chars: Map<Char, String> = mapOf(
        'a' to ".-",
        'b' to "-...",
        'c' to "-.-.",
        'd' to "-..",
        'e' to ".",
        'f' to "..-.",
        'g' to "--.",
        'h' to "....",
        'i' to "..",
        'j' to ".---",
        'k' to "-.-",
        'l' to ".-..",
        'm' to "--",
        'n' to "-.",
        'o' to "---",
        'p' to ".--.",
        'q' to "--.-",
        'r' to ".-.",
        's' to "...",
        't' to "-",
        'u' to "..-",
        'v' to "...-",
        'w' to ".--",
        'x' to "-..-",
        'y' to "-.--",
        'z' to "--..",
        '0' to "-----",
        '1' to ".----",
        '2' to "..---",
        '3' to "...--",
        '4' to "....-",
        '5' to ".....",
        '6' to "-....",
        '7' to "--...",
        '8' to "---..",
        '9' to "----."
    )
}