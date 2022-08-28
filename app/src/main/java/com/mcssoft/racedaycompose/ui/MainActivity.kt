package com.mcssoft.racedaycompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import com.mcssoft.racedaycompose.ui.theme.RaceDayComposeTheme
import com.mcssoft.racedaycompose.utility.network.ConnectionLiveData
import com.mcssoft.racedaycompose.utility.network.ConnectivityManager
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var connectivityManager: ConnectivityManager

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isNetworkAvailable = connectivityManager.isNetworkAvailable.value

            RaceDayComposeTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

}
