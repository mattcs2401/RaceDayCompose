package com.mcssoft.racedaycompose.ui.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Local Toast convenience method.
 * @param text: The text to display.
 * @param duration: The display duration (defaults Toast.LENGTH_SHORT).
 */
@Composable
fun Toast(
    text: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(LocalContext.current, text, duration).show()
}