package com.kaelmoreno.compose.composemultiplatformbase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Initialize AndroidContextProvider with application context
        AndroidContextProvider.initialize(this)

        // Initialize logger
        Logger.initialize()
        Logger.i("MainActivity created", "Android")

        setContent {
            App()
        }
    }
}