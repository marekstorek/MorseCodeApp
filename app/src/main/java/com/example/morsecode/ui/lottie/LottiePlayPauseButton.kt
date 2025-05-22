package com.example.morsecode.ui.lottie

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
fun LottiePlayPauseButton(
    isPlaying: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.playpauss))


    val dynamicProps = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.onPrimary.toArgb(),
            keyPath = arrayOf("**"),
        ),
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        speed = if (isPlaying) 1.5f else -1.5f,
        iterations = 1,
        restartOnPlay = false,
    )


    RoundedButton(onClick = onToggle, modifier = modifier) {
        LottieAnimation(
            composition = composition,
            dynamicProperties = dynamicProps,
            progress = { progress },
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
        )
    }
}
