package com.learn.gamesearchcompose.presentation.ui.hourglass

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HourGlass(
    hourGlassViewModel: HourGlassViewModel,
    hourGlassConfig: HourGlassConfig,
) {
    val (size) = hourGlassConfig
    val tick: Float by hourGlassViewModel.getTick.collectAsStateWithLifecycle(0f)
    BoxWithConstraints(
        modifier = Modifier.size(size),
    ) {
        val center = Offset(
            maxWidth.toPx() / 2,
            maxHeight.toPx() / 2
        )
        Canvas(
            modifier = Modifier,
        ) {
            topGlass(
                center,
                hourGlassConfig
            )
            bottomGlass(
                center,
                hourGlassConfig
            )
            topSand(
                center,
                hourGlassConfig,
                tick
            )
            bottomSand(
                center,
                hourGlassConfig,
                tick
            )
            movingSand(
                tick,
                center,
                hourGlassConfig
            )
            movingSandParticles(
                tick,
                center,
                hourGlassConfig
            )
        }
    }
}

@Preview
@Composable
fun HourGlassPreview() {
    val hourGlassViewModel = HourGlassViewModel()
    val hourGlassConfig = HourGlassConfig(
        size = 200.dp,
        sandWidth = 1.5.dp
    )
    HourGlass(
        hourGlassViewModel,
        hourGlassConfig
    )
}

