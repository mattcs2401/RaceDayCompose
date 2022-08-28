package com.mcssoft.racedaycompose.ui.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            when (state.status) {
                is SplashState.Status.Initialise -> {}
                is SplashState.Status.Loading -> {
                    LoadingDialog(
                        titleText = stringResource(id = R.string.dlg_init_title),
                        msgText = state.loadingMsg,
                        onDismiss = {}
                    )
                }
                is SplashState.Status.Failure -> {
                    /** TODO - some sort of retry. **/
                }
                is SplashState.Status.Success -> {
                    if (state.baseFromApi && (!state.runnerFromApi)) {
                        viewModel.setupRunnersFromApi(LocalContext.current)
                        // Runners will continue to load in the background.
                        LaunchedEffect(key1 = true) {
                            navigator.navigate(MeetingsScreenDestination)
                        }
                    }
//                    if (state.baseFromApi && state.runnerFromApi) {
//                        LaunchedEffect(key1 = true) {
//                            navigator.navigate(MeetingsScreenDestination)
//                        }
//                    }
                }
            }
        }
    }
}