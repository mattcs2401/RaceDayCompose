package com.mcssoft.racedaycompose.ui.summary

import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.mcssoft.racedaycompose.ui.summary.components.SummaryHeader
import com.mcssoft.racedaycompose.ui.summary.components.SummaryItem
import com.mcssoft.racedaycompose.ui.theme.RoundedCornerShapes
import com.mcssoft.racedaycompose.ui.theme.eightyPercent
import com.mcssoft.racedaycompose.ui.theme.padding16dp
import com.mcssoft.racedaycompose.ui.theme.twentyPercent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalFoundationApi::class)    // sticky header.
@Composable
@Destination
fun SummaryScreen(
    navigator: DestinationsNavigator,
    viewModel: SummaryViewModel = hiltViewModel(),
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
        if (state.count > 0) {
            // Only if there is something to display.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.secondary)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    state.grouped.forEach { (mtgCode, listing) ->
                        stickyHeader {
                            SummaryHeader(
                                mtgCode = mtgCode,
                                venueName = listing[0].venueName
                            )
                        }
                        items(
                            items = listing
                        ) { summary ->
                            SummaryItem(
                                summary = summary
                            )
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxWidth()) {
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