package com.example.morsecode.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.morsecode.ui.lottie.LottieMinimizeButton
import kotlinx.coroutines.launch

@Composable
fun MorseCodeDisplay(
    morseCode: String,
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onExpandedChange: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .animateContentSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            Log.i("Long", "press")
                            if (morseCode.isNotEmpty()) {
                                clipboardManager.setText(AnnotatedString(morseCode))
                                coroutineScope.launch {
                                    Toast.makeText(
                                        context,
                                        "Copied to clipboard",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    )
                }
                .then(if (!isExpanded) Modifier.height(0.dp) else Modifier)
        ) {

            val textStyle = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 16.sp,
                textAlign = TextAlign.Justify
            )
            val hintStyle = TextStyle(
                color = Color.Gray,
                fontStyle = FontStyle.Italic
            )


            Text(
                text = if (morseCode.isNotEmpty()) morseCode else "Translated code will be shown there",
                style = if (morseCode.isNotEmpty()) textStyle else hintStyle,
                overflow = TextOverflow.Clip,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .padding(end = 24.dp)

            )
        }
        LottieMinimizeButton(isExpanded, { onExpandedChange() }, Modifier.align(Alignment.TopEnd))
    }
}
