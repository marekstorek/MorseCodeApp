package com.example.morsecode.ui.components

import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SlashableIcon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    slashColor: Color = MaterialTheme.colorScheme.onPrimary,
    slashWidth: Dp = 5.dp,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    borderWidth: Dp = 4.dp
) {
    val progress by animateFloatAsState(
        targetValue = if (selected) 0f else 1f,
        animationSpec = tween(durationMillis = 500, easing = EaseInOutBack)
    )
    RoundedButton(modifier = modifier.drawWithContent {
        drawContent()

        if (progress > 0f) {
            val padX = 30.dp.toPx()
            val padY = 20.dp.toPx()
            val start = Offset(padX, padY)
            val end = Offset(size.width - padX, size.height - padY)
            val currentEnd = Offset(
                x = start.x + (end.x - start.x) * progress,
                y = start.y + (end.y - start.y) * progress
            )

            // Outer slash
            drawLine(
                color = borderColor,
                start = start,
                end = currentEnd,
                strokeWidth = slashWidth.toPx() + 2 * borderWidth.toPx(),
                cap = StrokeCap.Round
            )
            // Inner slash
            drawLine(
                color = slashColor,
                start = start,
                end = currentEnd,
                strokeWidth = slashWidth.toPx(),
                cap = StrokeCap.Round
            )
        }
    }, onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        )
    }

}
