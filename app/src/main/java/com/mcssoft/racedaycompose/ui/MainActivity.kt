package com.mcssoft.racedaycompose.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.mcssoft.racedaycompose.ui.components.navigation.NavGraph
import com.mcssoft.racedaycompose.ui.theme.RaceDayComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaceDayComposeTheme {
//                Surface { //(color = MaterialTheme.colors.background) {
                    NavGraph()
//                }
            }
        }
    }

}
