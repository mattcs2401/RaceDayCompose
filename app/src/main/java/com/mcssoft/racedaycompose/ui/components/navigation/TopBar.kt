package com.mcssoft.racedaycompose.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Implement a custom TopAppBar component.
 * @note onBackPressed and navIcon should implemented together.
 */
@Composable
fun TopBar(
    title: String,                        // title, required.
    bkgColour: Color,                     // background colour, required.
    onBackPressed: () -> Unit = {},       // onClick handler (optional).
    navIcon: ImageVector? = null          // nav icon (optional).
) {
    TopAppBar(
        title = { Text(title) },
        backgroundColor = bkgColour,
        navigationIcon = {
            if (onBackPressed != {} && navIcon != null) {
                IconButton(onClick = onBackPressed) {
                    Icon(navIcon, navIcon.toString())
                }
            }
        },
        actions = {
            // TODO - Any actions ?
        }
    )
}