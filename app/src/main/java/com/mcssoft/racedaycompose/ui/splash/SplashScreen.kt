package com.mcssoft.racedaycompose.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.mcssoft.racedaycompose.ui.ScreenRoute

@Composable
fun SplashScreen(navController: NavController,
                   viewModel: SplashViewModel = hiltViewModel()
) {
    /**
     * Notes:
     * The SplashScreen is only meant to be called once when the app loads for the first time.
     **/

    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {
        if(state.error.isNotBlank()) {
            // TODO - This needs to be better (how?). Also some sort of retry ?
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if(state.initialising) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if(state.finished) {
            // Initialisation completed ok. Nav to Meetings screen.
//            /**
//             * TODO - clunky work around.
//             * Doco - "You should only call navigate() as part of a callback and not as part of
//             *         your composable itself, to avoid calling navigate() on every recomposition.
//             */
            LaunchedEffect(key1 = true) {
                // Note: key1=true is just some arbitrary value (for this).
                navController.navigate(ScreenRoute.MeetingsScreen.route)
            }
        }
    }

}

