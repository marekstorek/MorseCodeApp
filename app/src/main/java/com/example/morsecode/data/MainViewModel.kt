package com.example.morsecode.data

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import kotlin.math.pow

class MorseViewModel(application: Application) : AndroidViewModel(application) {
    val morsePlayer = MorsePlayer(application)

    private val _inputText = mutableStateOf("")
    val inputText: State<String> = _inputText

    private val _sliderPosition = mutableStateOf(0.5f)
    val sliderPosition: State<Float> = _sliderPosition

    private val _isTranslationExpanded = mutableStateOf(true)
    val isTranslationExpanded: State<Boolean> = _isTranslationExpanded


    val flashingOn: State<Boolean> = morsePlayer.flashingOn
    val vibratingOn: State<Boolean> = morsePlayer.vibratingOn
    val soundOn: State<Boolean> = morsePlayer.soundOn
    val translatedText: State<String> = morsePlayer.translatedText
    val currentLetter: State<Char> = morsePlayer.currentLetter
    val playerStatus: State<MorsePlayer.Status> = morsePlayer.status

    fun onInputTextChanged(newText: String) {
        _inputText.value = newText
    }

    fun onSliderPositionChanged(newPosition: Float) {
        _sliderPosition.value = newPosition
        morsePlayer.speedLevel.value = 0.25f * 16f.pow(1f - newPosition)
    }

    fun toggleFlash() = morsePlayer.toggleFlash()
    fun toggleVibration() = morsePlayer.toggleVibration()
    fun toggleSound() = morsePlayer.toggleSound()
    fun togglePlay() = morsePlayer.togglePlay(_inputText.value)
    fun toggleStop() = morsePlayer.toggleStop()

    fun toggleTranslationExpanded(){
        _isTranslationExpanded.value = !_isTranslationExpanded.value
    }

    override fun onCleared() {
        super.onCleared()
         morsePlayer.release()
    }
}