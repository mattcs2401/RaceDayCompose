package com.mcssoft.racedaycompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mcssoft.racedaycompose.ui.components.navigation.NavGraph
import com.mcssoft.racedaycompose.ui.theme.RaceDayComposeTheme
import com.mcssoft.racedaycompose.utility.network.ConnectivityManager
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
            // TODO - integrate network availability.
            val isNetworkAvailable = connectivityManager.isNetworkAvailable.value

            RaceDayComposeTheme {
                NavGraph(applicationContext)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

}
