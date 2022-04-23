package com.mcssoft.racedaycompose.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.ScreenRoute
import com.mcssoft.racedaycompose.ui.components.CheckBox
import com.mcssoft.racedaycompose.ui.components.LoadingDialog
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
                title = stringResource(id = R.string.label_preferences),
                MaterialTheme.colors.primary,
                onBackPressed = {
                    navController.navigate(ScreenRoute.MeetingsScreen.route) {
                        popUpTo(ScreenRoute.MeetingsScreen.route) {
                            inclusive = true
                        }
                    }
                },
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
                    CheckBox(
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
                    CheckBox(
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
                LoadingDialog(
                    titleText = stringResource(id = R.string.dlg_loading_title),
                    msgText = stringResource(id = R.string.dlg_loading_msg)
                )
            }
        }
    }

}