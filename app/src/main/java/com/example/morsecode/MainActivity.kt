package com.example.morsecode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.morsecode.data.MorseViewModel
import com.example.morsecode.ui.MainScreen
import com.example.morsecode.ui.theme.MorseCodeTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MorseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MorseCodeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(viewModel)
                }
            }
        }
    }

}


