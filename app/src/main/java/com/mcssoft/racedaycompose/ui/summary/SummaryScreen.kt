package com.mcssoft.racedaycompose.ui.summary

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.components.navigation.TopBar
import com.mcssoft.racedaycompose.ui.destinations.MeetingsScreenDestination
import com.mcssoft.racedaycompose.ui.summary.components.SummaryItem
import com.mcssoft.racedaycompose.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination
fun SummaryScreen(
    navigator: DestinationsNavigator,
    viewModel: SummaryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_summary),
                backgroundColour = MaterialTheme.colors.primary,
                backNavIcon = R.drawable.ic_arrow_back_24,
                onBackPressed = {
                    navigator.navigate(MeetingsScreenDestination.route) {
                        popUpTo(route = MeetingsScreenDestination.route) {
                            inclusive = true
                        }
                    }
                },
            )
        },
        backgroundColor = Color.LightGray
    ) {
        if(state.count > 0) {
            // Only if there is something to display.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.secondary)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding64dp)
                ) {
                    items(
                        items = state.summaries
                    ) { summary ->
                        SummaryItem(
                            summary = summary
                        )
                    }

                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = padding64dp)
            ) {
                NothingToDisplay()
            }
        }
    }

}

@Composable
fun NothingToDisplay() {
    Card(
        shape = RoundedCornerShapes.medium
    ) {
        // A single columns with 3 Rows and Spacers in between.
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
                        painter = painterResource(R.drawable.ic_warning_48),
                        contentDescription = "dialog icon"
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(eightyPercent),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Nothing to display.",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}