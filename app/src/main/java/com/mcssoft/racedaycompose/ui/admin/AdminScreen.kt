package com.mcssoft.racedaycompose.ui.admin

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.components.navigation.Screen
import com.mcssoft.racedaycompose.ui.components.navigation.TopBar
import com.mcssoft.racedaycompose.ui.theme.RoundedCornerShapes
import com.mcssoft.racedaycompose.ui.theme.eightyPercent
import com.mcssoft.racedaycompose.ui.theme.padding16dp
import com.mcssoft.racedaycompose.ui.theme.twentyPercent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminScreen(
    navController: NavController,
    viewModel: AdminViewModel = hiltViewModel()
) {
//    val state by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_admin),
                backgroundColour = MaterialTheme.colors.primary,
                backNavIcon = R.drawable.ic_arrow_back_24,
                onBackPressed = {
                    navController.navigate(Screen.MeetingsScreen.route) {
                        popUpTo(route = Screen.MeetingsScreen.route) {
                            inclusive = true
                        }
                    }
                },
            )
        },
        backgroundColor = Color.LightGray
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            NothingToDisplay()
        }
    }
}

@Composable
fun NothingToDisplay() {
    Card(
        shape = RoundedCornerShapes.medium
    ) {
        // A single column, with one Row divided into two columns.
        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .background(backgroundColour)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding16dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(twentyPercent),
                    horizontalAlignment = Alignment.Start
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_under_const_48),
                        contentDescription = "dialog icon"
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(eightyPercent),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Nothing to display at this time.",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}