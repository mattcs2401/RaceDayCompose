package com.mcssoft.racedaycompose.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.components.dialog.LoadingDialog
import com.mcssoft.racedaycompose.ui.destinations.MeetingsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * App starting point.
 *
 * ------
 * Notes:
 * ------
 * The Compose navigation doesn't like the startDestination to have any nav arguments, even
 * default values. This screen was implemented to allow for:
 * 1. the MeetingsScreen (the actual home/startDestination) to have nav arguments, and
 * 2. any pre-population in the database as required.
 */
@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    when(state.status) {
        is SplashState.Status.Initialise -> {}
        is SplashState.Status.Loading -> {
            LoadingDialog(
                titleText = stringResource(id = R.string.dlg_init_title),
                msgText = stringResource(id = R.string.dlg_loading_msg),
                onDismiss = {}
            )
        }
        is SplashState.Status.Failure -> {
            // TBA.
        }
        is SplashState.Status.Success -> {
            if(state.prePopulated) {
                LaunchedEffect(key1 = true) {
                    navigator.navigate(MeetingsScreenDestination)
                }
            } else {
                viewModel.prePopulate()
            }
        }
    }

}