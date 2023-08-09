package com.learn.gamesearchcompose.presentation.ui.hourglass

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.min
import kotlin.random.Random

data class HourGlassConfig(
    val size: Dp,
    val glassWidth: Dp = 2.dp,
    val glassColor: Color = Color(0xFF966F33),
    val glassGapForSand: Dp = 10.dp,
    val sandColor: Color = Color(0xFFC2B280),
    val sandWidth: Dp = 2.5.dp,
)

@Composable
fun Dp.toPx() = with(LocalDensity.current) { this@toPx.toPx() }

// This function draws the top glass frame of the hourglass.
fun DrawScope.topGlass(
    center: Offset,
    hourGlassConfig: HourGlassConfig,
) {
    with(hourGlassConfig) {
        val length = center.x

        // Create a Path to define the shape of the top glass frame
        drawPath(
            path = Path().apply {
                moveTo(center.x - length, center.y - length)
                lineTo(center.x + length, center.y - length)
                lineTo(center.x + glassGapForSand.value / 2, center.y)
                moveTo(center.x - glassGapForSand.value / 2, center.y)
                lineTo(center.x - length, center.y - length)
            },
            color = glassColor,
            style = Stroke(width = glassWidth.value)
        )
    }
}

// This function draws the bottom glass frame of the hourglass.
fun DrawScope.bottomGlass(
    center: Offset,
    hourGlassConfig: HourGlassConfig,
) {
    // Rotate the canvas by 180 degrees to draw the bottom glass
    rotate(
        degrees = 180f,
        pivot = center
    ) {
        topGlass(center, hourGlassConfig)
    }
}

// This function draws the sand in the top half of the hourglass.
fun DrawScope.topSand(
    center: Offset,
    hourGlassConfig: HourGlassConfig,
    tick: Float = 0f,
) {
    with(hourGlassConfig) {
        val length = center.x
        var variableLength = length - glassWidth.value / 2
        variableLength -= variableLength * tick

        // Create a Path to define the shape of the sand in the top half
        drawPath(
            path = Path().apply {
                moveTo(center.x - variableLength, center.y - variableLength)
                lineTo(center.x + variableLength, center.y - variableLength)
                lineTo(center.x, center.y + glassWidth.value)
                moveTo(center.x, center.y)
                lineTo(center.x - variableLength, center.y - variableLength)
            },
            color = sandColor,
        )
    }
}

// This function draws a filled rectangle at a specified position.
fun DrawScope.filledRect(
    center: Offset,
    rectLength: Float,
    color: Color,
) {
    // Create a Path to define the shape of the filled rectangle
    drawPath(
        path = Path().apply {
            moveTo(center.x + rectLength, center.y + rectLength)
            lineTo(center.x - rectLength, center.y + rectLength)
            lineTo(center.x + rectLength, center.y)
            moveTo(center.x + rectLength, center.y)
            lineTo(center.x - rectLength, center.y)
            lineTo(center.x - rectLength, center.y + rectLength)
        },
        brush = SolidColor(color),
    )
}

// This function draws the sand in the bottom half of the hourglass.
fun DrawScope.bottomSand(
    center: Offset,
    hourGlassConfig: HourGlassConfig,
    tick: Float,
) {
    with(hourGlassConfig) {
        val clipLength = center.x - glassWidth.value / 2

        // Clip the canvas to draw sand only within the bottom glass
        clipPath(
            path = Path().apply {
                moveTo(center.x + clipLength, center.y + clipLength)
                lineTo(center.x - clipLength, center.y + clipLength)
                lineTo(center.x, center.y - glassWidth.value)
                moveTo(center.x, center.y)
                lineTo(center.x + clipLength, center.y + clipLength)
            },
            clipOp = ClipOp.Intersect
        ) {
            val rectLength = center.x * (1f - tick)
            filledRect(center, center.x, hourGlassConfig.sandColor)
            filledRect(center, rectLength, Color.White)
        }
    }
}

// This function draws the continuous flow of sand in the hourglass.
fun DrawScope.movingSand(
    tick: Float,
    center: Offset,
    hourGlassConfig: HourGlassConfig,
) {
    if (tick > 0f) {
        // Draw a line representing the falling sand
        drawLine(
            color = Color(0xFFC2B280),
            start = center,
            end = Offset(center.x, center.y + center.x - (hourGlassConfig.glassWidth.value / 2)),
            strokeWidth = hourGlassConfig.sandWidth.toPx()
        )
    }
}

// This function draws moving sand particles in the hourglass.
fun DrawScope.movingSandParticles(
    tick: Float,
    center: Offset,
    hourGlassConfig: HourGlassConfig,
) {
    if (tick > 0f) {
        // Draw multiple sand particles using random offsets
        for (i in 0..10) {
            val random = Random.nextInt(center.y.dp.toPx().toInt())
            drawPoints(
                pointMode = PointMode.Points,
                points = listOf(
                    Offset(center.x - 1.dp.toPx(), min(center.y + random.toFloat(), center.y + center.x) - hourGlassConfig.glassWidth.value * 2),
                    Offset(center.x + 1.dp.toPx(), min(center.y + random.toFloat(), center.y + center.x) - hourGlassConfig.glassWidth.value * 2),
                ),
                color = hourGlassConfig.sandColor,
                strokeWidth = 4f
            )
        }
    }
}




