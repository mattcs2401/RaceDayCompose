package com.mcssoft.racedaycompose.ui.summary.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.mcssoft.racedaycompose.ui.theme.*

@Composable
fun SummaryHeader(
    mtgCode: String,
    venueName: String
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding4dp),
            elevation = elevation4dp
        ) {
            // 1 row with 2 columns.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding16dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Column 1 (meeting code).
                Column(
                    modifier = Modifier.fillMaxWidth(twentyPercent),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = mtgCode,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6
                    )
                }
                // Column 2 (venue name).
                Column(
                    modifier = Modifier.fillMaxWidth(eightyPercent),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = venueName,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}