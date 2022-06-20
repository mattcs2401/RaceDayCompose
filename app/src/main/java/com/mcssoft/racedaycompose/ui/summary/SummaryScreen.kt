package com.mcssoft.racedaycompose.ui.summary

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun SummaryScreen(
    navigator: DestinationsNavigator,
//    navController: NavController,
    viewModel: SummaryViewModel = hiltViewModel()
) {}