package com.example.morsecode.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@Composable
fun RoundedButton(modifier: Modifier = Modifier, onClick: () -> Unit, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .aspectRatio(1.5f)
            .clip(RoundedCornerShape(percent = 40))
            .background(
                MaterialTheme.colorScheme.primary
            )
            .clickable(onClick = onClick, interactionSource = null, indication = null),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
