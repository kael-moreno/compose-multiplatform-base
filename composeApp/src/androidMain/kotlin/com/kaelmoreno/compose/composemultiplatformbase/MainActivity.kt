package com.kaelmoreno.compose.composemultiplatformbase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kaelmoreno.compose.composemultiplatformbase.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Initialize AndroidContextProvider with application context
        AndroidContextProvider.initialize(this)

        // Initialize Koin
        startKoin {
            androidContext(this@MainActivity)
            modules(appModules)
        }

        // Initialize logger
        Logger.initialize()
        Logger.i("MainActivity created with Koin initialized", "Android")

        setContent {
            App()
        }
    }
}