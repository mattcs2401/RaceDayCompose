package com.mcssoft.racedaycompose.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

@Composable
fun TopBar(title: String,                        // title, required.
           bkgColour: Color,                     // background colour, required.
           navController: NavController? = null, // nav controller, optional.
           navIcon: ImageVector? = null          // nav icon, optional.
) {
    TopAppBar(
        title = { Text(title) },
        backgroundColor = bkgColour,
        // Parameters 'navController' and 'navIcon' must both be not Null for back nav action.
        navigationIcon = {
            if(navController != null && navIcon != null) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(navIcon, navIcon.toString())
                }
            }
        },
        actions = {}
        // TODO - Any actions ?
    )
}