package com.mcssoft.racedaycompose.ui.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.components.navigation.Screen
import com.mcssoft.racedaycompose.ui.components.navigation.TopBar
import com.mcssoft.racedaycompose.ui.destinations.MeetingsScreenDestination
import com.mcssoft.racedaycompose.ui.settings.components.CheckBoxSettingsItem
import com.mcssoft.racedaycompose.ui.theme.height8dp
import com.mcssoft.racedaycompose.ui.theme.padding16dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun SettingsScreen(
    navController: DestinationsNavigator,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val fromDbState by viewModel.fromDbState.collectAsState()
    val onlyAuNzState by viewModel.onlyAuNzState.collectAsState()

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_preferences),
                backgroundColour = MaterialTheme.colors.primary,
                backNavIcon = R.drawable.ic_arrow_back_24,
                onBackPressed = {
                    navController.navigate(MeetingsScreenDestination) {
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
                    checked = fromDbState.currValue,
                    onCheckedChange = { checked ->
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
                    checked = onlyAuNzState.currValue,
                    onCheckedChange = { checked ->
                        viewModel.onEvent(SettingsEvent.SaveOnlyAuNzPref(checked))
                    },
                    backgroundColour = MaterialTheme.colors.secondary
                )
            }

        }
    }

}
