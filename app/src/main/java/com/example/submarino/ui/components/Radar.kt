package com.example.submarino.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.submarino.data.Punto
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Radar(
    modifier: Modifier = Modifier,
    size: Float = 380f,
    position: Float = 0f,
    objects: List<Punto> = listOf()
) {
    val angular = 0.0
    Canvas(modifier = modifier
        .height(size.dp)
        .width(size.dp)
        .background(color = Color.Black)
        .drawWithCache {
            onDrawBehind {
                val center = Offset(size.dp.toPx() / 2f, size.dp.toPx() / 2f)
                val maxRad = ((size - 2f) / 2f)
                var p = 0f
                while (p <= maxRad) {
                    drawCircle(
                        color = Color.Green,
                        radius = p.dp.toPx(),
                        style = Stroke(width = 1.dp.toPx())
                    )
                    p += (maxRad / 3f)
                }
                var a = 0.0
                while (a < 2 * PI) {
                    val destino = Offset(
                        (center.x + (maxRad.dp.toPx() * cos(a))).toFloat(),
                        (center.y - (maxRad.dp.toPx() * sin(a))).toFloat()
                    )
                    drawLine(
                        color = Color.Green,
                        center,
                        destino,
                        strokeWidth = 1.dp.toPx()
                    )
                    a += (PI / 6)
                }
            }
        }
        .graphicsLayer {
            this.rotationZ = position
        },
        onDraw = {
            val center = Offset(size.dp.toPx() / 2f, size.dp.toPx() / 2f)
            val maxRad = ((size - 2f) / 2f)
            var a = angular - (PI / 3)
            while (a < angular) {
                val destino = Offset(
                    (center.x + (maxRad.dp.toPx() * cos(a))).toFloat(),
                    (center.y - (maxRad.dp.toPx() * sin(a))).toFloat()
                )
                drawLine(
                    color = Color(
                        0,
                        255,
                        0,
                        alpha = (200 - (3.33 * ((angular - a) * (180.0 / PI)))).toInt()
                    ), center, destino, strokeWidth = 1.dp.toPx()
                )
                a += (PI / 180)
            }
            objects.forEach {
                drawCircle(color = Color.Red, center= Offset(it.x, it.y))
            }
        }
    )
}

@Preview
@Composable
fun RadarPreview() {
    Radar()
}