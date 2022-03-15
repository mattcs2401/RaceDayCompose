package com.mcssoft.racedaycompose.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
    val fromDbState = viewModel.fromDbState
    val onlyAuNzState = viewModel.onlyAuNzState

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                "Settings",
                MaterialTheme.colors.primary,
                onBackPressed = { navController.popBackStack() },
                Icons.Filled.ArrowBack
            )
        },
        backgroundColor = Color.LightGray
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary))
        {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                ) {
                    DefaultCheckBox(
                        text = stringResource(id = R.string.pref_load_from_db),
                        selected = fromDbState.value.preference,
                        onCheckedChange = { checked ->
                            viewModel.onEvent(SettingsEvent.SaveFromDbPref(checked))
                        }
                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                ) {
                    DefaultCheckBox(
                        text = stringResource(id = R.string.pref_only_au),
                        selected = onlyAuNzState.value.preference,
                        onCheckedChange = { checked ->
                            viewModel.onEvent(SettingsEvent.SaveOnlyAuNzPref(checked))
                        }
                    )
                }
            }
            if (fromDbState.value.error.isNotBlank()) {
                Text(
                    text = fromDbState.value.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.medium)
                        .align(Alignment.Center)
                )
            }
            if(fromDbState.value.loading) {
                Loading("Loading ...")
            }
        }
    }

}