package com.mcssoft.racedaycompose.ui.components

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

@Composable
fun HandleBackPressed(backPressedDispatcher: OnBackPressedDispatcher) {
    val callback = remember {
        object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do nothing.
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher.addCallback(callback)
        onDispose {
            callback.remove()
        }
    }
}