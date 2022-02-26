package com.mcssoft.racedaycompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import com.mcssoft.racedaycompose.ui.components.Root
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

//            val materialBlue700= Color(0xFF1976D2)
//            val scaffoldState = rememberScaffoldState()
//            Scaffold(
//                scaffoldState = scaffoldState,
//                topBar = {
//                    TopAppBar(title = {
//                        Text("TopAppBar")
//                    },
//                    backgroundColor = materialBlue700)
//                },
//                content = {
                    Root(window = window)
//                },
//                bottomBar = {
//                    BottomAppBar(backgroundColor = materialBlue700) {
//                        Text("BottomAppBar")
//                    }
//                }
//
//            )
        }
    }

}
