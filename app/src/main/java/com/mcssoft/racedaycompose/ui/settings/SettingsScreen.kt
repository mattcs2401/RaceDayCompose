package com.mcssoft.racedaycompose.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.ui.components.DefaultCheckBox
import com.mcssoft.racedaycompose.ui.components.TopBar
import com.mcssoft.racedaycompose.ui.theme.custom.spacing

@Composable
fun SettingsScreen(navController: NavController,
                   viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state//.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar("Settings", MaterialTheme.colors.primary, navController, Icons.Default.ArrowBackIos)
        },
        backgroundColor = Color.LightGray
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary))
        {
            ConstraintLayout {

                val (idLabel, idCBox) = createRefs()

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(idCBox) {
                        top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(parent.start, margin = 8.dp)
                    }
                ) {
                    DefaultCheckBox(
                        text = "Load From DB",
                        selected = state.value.fromDbPref,
                        onCheckedChange = { checked ->
                            //state.value = SettingsState(fromDbPref = checked)
                            viewModel.onEvent(SettingsEvent.SaveFromDbPref(checked))
                        }
                    )
                }

//                Text(text = "Load From DB",
//                    modifier = Modifier.constrainAs(idLabel) {
//                        top.linkTo(parent.top, margin = 16.dp)
//                        start.linkTo(parent.start, margin = 16.dp)
//                })
//
//                Checkbox(
//                    checked = state.fromDbPref,
//                    onCheckedChange = { state.fromDbPref = it
//                        viewModel.onEvent(SettingsEvent.SaveFromDbPref()) },
//                    modifier = Modifier.constrainAs(idCBox) {
//                        top.linkTo(parent.top, margin = 16.dp)
//                        start.linkTo(idLabel.end, margin = 16.dp)
//                    },
//
//                )
            }
            if (state.value.error.isNotBlank()) {
                Text(
                    text = state.value.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.medium)
                        .align(Alignment.Center)
                )
            }
            if(state.value.fromDbPref) {
                // TBA
            }
        }
    }

}