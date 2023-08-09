package com.learn.gamesearchcompose.presentation.ui.hourglass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HourGlassScreen(hourGlassViewModel: HourGlassViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HourGlass(
            hourGlassViewModel,
            hourGlassConfig = HourGlassConfig(
                size = 100.dp,
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 40.dp,
                    vertical = 40.dp
                ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ElevatedButton(
                onClick = {
                    hourGlassViewModel.startTimer(15)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Start")
            }
            Spacer(modifier = Modifier.width(16.dp))
            ElevatedButton(
                onClick = {
                    hourGlassViewModel.stopTimer()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "End")
            }
        }
    }
}
