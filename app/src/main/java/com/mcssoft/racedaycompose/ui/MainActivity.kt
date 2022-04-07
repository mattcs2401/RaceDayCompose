package com.mcssoft.racedaycompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mcssoft.racedaycompose.ui.components.Navigation
import com.mcssoft.racedaycompose.ui.theme.framework.RaceDayComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaceDayComposeTheme {
                Surface(color = MaterialTheme.colors.background) {

                    Navigation(applicationContext, lifecycleOwner())
                }
            }
        }
    }

    private fun lifecycleOwner(): LifecycleOwner {
        return this
    }

}
