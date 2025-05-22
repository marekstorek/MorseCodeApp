package com.example.morsecode.ui.lottie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@Composable
fun LottieMinimizeButton(
    isExpanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.minimize))

    val dynamicProps = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.onPrimary.toArgb(),
            keyPath = arrayOf("**"),
        )
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        speed = if (isExpanded) 1.5f else -1.5f,
        iterations = 1,
        restartOnPlay = false
    )

    Box(
        modifier = modifier
            .size(44.dp)
            .padding(4.dp)
            .clip(CircleShape)
            .clickable(onClick = onToggle),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            dynamicProperties = dynamicProps,
            progress = { progress },
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
