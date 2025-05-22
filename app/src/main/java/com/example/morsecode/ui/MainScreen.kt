package com.example.morsecode.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.morsecode.data.MorsePlayer
import com.example.morsecode.data.MorseViewModel
import com.example.morsecode.ui.components.SlashableIcon
import com.example.morsecode.ui.components.ButtonsRow
import com.example.morsecode.ui.components.CurrentLetterDisplay
import com.example.morsecode.ui.components.MorseCodeDisplay
import com.example.morsecode.ui.lottie.LottiePlayPauseButton
import com.example.morsecode.ui.lottie.LottieStopButton

@Composable
fun MainScreen(viewModel: MorseViewModel) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    fun clearFocusAndHideKeyboard() {
        keyboardController?.hide()
        focusManager.clearFocus()
    }

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "Morse Code Translator",
            fontSize = 22.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W800
        )

        val input by viewModel.inputText
        OutlinedTextField(
            value = input,
            onValueChange = { viewModel.onInputTextChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5
        )

        ToolsButtons(viewModel)

        val sliderPosition by viewModel.sliderPosition
        SpeedSlider(
            sliderPosition = sliderPosition,
            onValueChange = { viewModel.onSliderPositionChanged(it)}
        )


        val playerStatus by viewModel.playerStatus

        val currentLetter by viewModel.currentLetter
        CurrentLetterDisplay(
            currentLetter = currentLetter,
            isTranslating = playerStatus == MorsePlayer.Status.PLAYING,
        )

        val morseCode by viewModel.translatedText
        val isTranslationExpanded by viewModel.isTranslationExpanded
        MorseCodeDisplay(
            morseCode,
            isExpanded = isTranslationExpanded,
            onExpandedChange = { viewModel.toggleTranslationExpanded() })

        ControlsButtons(
            isPlaying = playerStatus == MorsePlayer.Status.PLAYING,
            onPlayPause = {
                viewModel.togglePlay();
                clearFocusAndHideKeyboard()
            },
            onStop = {
                viewModel.toggleStop();
                clearFocusAndHideKeyboard()
            }
        )

    }

}

@Composable
fun ToolsButtons(viewModel: MorseViewModel) {
    ButtonsRow {
        SlashableIcon(
            imageVector = Icons.Default.FlashlightOn,
            contentDescription = null,
            selected = viewModel.flashingOn.value,
            modifier = Modifier.weight(1f),
            onClick = { viewModel.toggleFlash() })
        SlashableIcon(
            imageVector = Icons.Default.Vibration,
            contentDescription = null,
            selected = viewModel.vibratingOn.value,
            modifier = Modifier.weight(1f),
            onClick = { viewModel.toggleVibration() })
        SlashableIcon(
            imageVector = Icons.Default.VolumeUp,
            contentDescription = null,
            selected = viewModel.soundOn.value,
            modifier = Modifier.weight(1f),
            onClick = { viewModel.toggleSound() })
    }
}

@Composable
fun ControlsButtons(
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onStop: () -> Unit
) {
    ButtonsRow() {

        LottiePlayPauseButton(
            isPlaying = isPlaying,
            onToggle = onPlayPause,
            modifier = Modifier.weight(1f)
        )
        LottieStopButton(
            isPlaying = true,
            onToggle = onStop,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SpeedSlider(sliderPosition: Float, onValueChange: (Float) -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text("0.25x", fontSize = 14.sp, modifier = Modifier.weight(1f))
            Text(
                "Speed",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 8.dp)
            )
            Text(
                "4x",
                textAlign = TextAlign.Right,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )
        }
        Slider(
            value = sliderPosition,
            onValueChange = { onValueChange(it) },
            steps = 0,
            valueRange = 0f..1f
        )
    }
}