package com.example.morsecode.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CurrentLetterDisplay(
    currentLetter: Char,
    isTranslating: Boolean,
    modifier: Modifier = Modifier
) {
    val letterHeight by animateDpAsState(targetValue = if (isTranslating) 48.dp else 0.dp)

    Text(
        text = currentLetter.toString().uppercase(),
        fontSize = 30.sp,
        fontFamily = FontFamily.Monospace,
        modifier = modifier
            .fillMaxWidth()
            .height(letterHeight)
            .animateContentSize(),
        textAlign = TextAlign.Center
    )
}
