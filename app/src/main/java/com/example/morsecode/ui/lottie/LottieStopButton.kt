package com.example.morsecode.ui.lottie

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.example.morsecode.R
import com.example.morsecode.ui.components.RoundedButton

@Composable
fun LottieStopButton(
    isPlaying: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.stop))


    val dynamicProps = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.onPrimary.toArgb(),
            keyPath = arrayOf("**"),
        ),
    )

    var direction by remember { mutableStateOf(false) }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        speed = if (direction) 1.5f else -1.5f,
        restartOnPlay = false,
    )

    RoundedButton(onClick = { onToggle(); direction = !direction }, modifier = modifier) {
        LottieAnimation(
            composition = composition,
            dynamicProperties = dynamicProps,
            progress = { progress },
            modifier = Modifier.padding(top = 5.dp, start = 10.dp)
        )
    }
}
