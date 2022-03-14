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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.components.DefaultCheckBox
import com.mcssoft.racedaycompose.ui.components.Loading
import com.mcssoft.racedaycompose.ui.components.TopBar
import com.mcssoft.racedaycompose.ui.theme.custom.spacing

@Composable
fun SettingsScreen(navController: NavController,
                   viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state
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

                val (idFromDb, idOnlyAu) = createRefs()

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(idFromDb) {
                        top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(parent.start, margin = 8.dp)
                    }
                ) {
                    DefaultCheckBox(
                        text = stringResource(id = R.string.pref_load_from_db),
                        selected = state.value.fromDbPref,
                        onCheckedChange = { checked ->
                            viewModel.onEvent(SettingsEvent.SaveFromDbPref(checked))
                        }
                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(idOnlyAu) {
                        top.linkTo(idFromDb.bottom, margin = 16.dp)
                        start.linkTo(idFromDb.start, margin = 0.dp)
                    }
                ) {
                    DefaultCheckBox(
                        text = stringResource(id = R.string.pref_only_au),
                        selected = state.value.onlyAuPref,
                        onCheckedChange = { checked ->
                            viewModel.onEvent(SettingsEvent.SaveOnlyAuPref(checked))
                        }
                    )
                }
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
            if(state.value.loading) {
                Loading("Loading ...")
            }
        }
    }

}