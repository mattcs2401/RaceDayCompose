package com.mcssoft.racedaycompose.ui.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.components.LoadingDialog
import com.mcssoft.racedaycompose.ui.components.navigation.Screen
import com.mcssoft.racedaycompose.ui.components.navigation.TopBar
import com.mcssoft.racedaycompose.ui.settings.components.CheckBoxSettingsItem
import com.mcssoft.racedaycompose.ui.theme.height8dp
import com.mcssoft.racedaycompose.ui.theme.padding16dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navController: NavController,
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
                backgroundColour = MaterialTheme.colors.primary,
                backNavIcon = R.drawable.ic_arrow_back_24,
                onBackPressed = {
                    navController.navigate(
                        Screen.MeetingsScreen.route + "prefsChange=${false}"
                    ) {
                        popUpTo(Screen.MeetingsScreen.route) {
                            inclusive = true
                        }
                    }
                },
                actions = {}
            )
        },
        backgroundColor = Color.LightGray
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)// MaterialTheme.colors.secondary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding16dp),
                verticalArrangement = Arrangement.Center
            ) {
                CheckBoxSettingsItem(
                    textTitle = stringResource(id = R.string.pref_load_from_db),
                    textDescription = "Load meeting details from local.",
                    selected = fromDbState.value.preference,
                    onChange = { checked ->
                        viewModel.onEvent(SettingsEvent.SaveFromDbPref(checked))
                    },
                    backgroundColour = MaterialTheme.colors.secondary
                )
                Spacer(
                    modifier = Modifier
                        .height(height8dp)
                        .fillMaxWidth()
                )
                CheckBoxSettingsItem(
                    textTitle = stringResource(id = R.string.pref_only_au),
                    textDescription = "Display only AU/NZ meeting details.",
                    selected = onlyAuNzState.value.preference,
                    onChange = { checked ->
                        viewModel.onEvent(SettingsEvent.SaveOnlyAuNzPref(checked))
                    },
                    backgroundColour = MaterialTheme.colors.secondary
                )
            }
            if (fromDbState.value.error.isNotBlank()) {
                Text(
                    text = fromDbState.value.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding16dp)
                        .align(Alignment.Center)
                )
            }
            if (fromDbState.value.loading) {
                LoadingDialog(
                    titleText = stringResource(id = R.string.dlg_loading_title),
                    msgText = stringResource(id = R.string.dlg_loading_msg)
                )
            }
        }
    }

}