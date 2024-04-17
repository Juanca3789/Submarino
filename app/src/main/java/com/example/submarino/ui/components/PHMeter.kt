package com.example.submarino.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp

@Composable
fun PHMeter(phValue: Double, modifier: Modifier = Modifier, sizeMeter: Float = 50f) {
    val textMeasurer = rememberTextMeasurer()
    val arrowColor = MaterialTheme.colorScheme.onBackground
    val brush = Brush.horizontalGradient(
        listOf(
            Color(0xFFED1B26),
            Color(0xFFF46432),
            Color(0xFFF68F1E),
            Color(0xFFFFC324),
            Color(0xFFFEF200),
            Color(0xFF83C241),
            Color(0xFF4DB749),
            Color(0xFF33A94B),
            Color(0xFF0AB8B6),
            Color(0xFF4590CF),
            Color(0xFF3853A4),
            Color(0xFF5A51A2),
            Color(0xFF63459D),
            Color(0xFF6C2180),
            Color(0xFF49176E)
        ))
    Canvas(
        modifier = modifier
            .height((sizeMeter + 30f).dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .drawWithCache {
                onDrawBehind {
                    drawRect(
                        brush,
                        size = Size(
                            size.width,
                            sizeMeter.dp.toPx()
                        )
                    )
                }
            }
            .graphicsLayer {
                this.translationX =
                    ((size.component1() / 14) * phValue).toFloat() - 18.dp.toPx() / 2
            }
    ){
        val path = Path()
        path.moveTo(5f, (sizeMeter + 15).dp.toPx())
        path.lineTo(5f + 15.dp.toPx()/2, sizeMeter.dp.toPx())
        path.lineTo(5f + 15.dp.toPx(), (sizeMeter + 15).dp.toPx())
        path.close()
        drawPath(path = path, color = arrowColor)
        drawText(
            textMeasurer,
            phValue.toString(),
            Offset(0f,  (sizeMeter + 15).dp.toPx()),
            style = TextStyle(
                color = arrowColor
            )
        )
    }
}