package com.example.submarino.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.submarino.ui.theme.SubmarinoTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Termometer(temperature: Float, height: Float = 400f, strokeWidth : Float = 3f, modifier: Modifier = Modifier) {
    val width: Float = height / 2
    val lineColor = MaterialTheme.colorScheme.onBackground
    Canvas(modifier = modifier
        .height(height.dp)
        .width(width.dp)
        .drawWithCache {
            onDrawWithContent {
                drawContent()
                val path = Path()
                path.addArc(
                    Rect(
                        Offset((width / 2).dp.toPx(), ((7 * height) / 8).dp.toPx()),
                        (height / 8).dp.toPx()
                    ), -50f, 280f
                )
                path.lineTo(
                    ((width / 2) + ((height / 8) * cos(40f))).dp.toPx(),
                    (height / 8).dp.toPx()
                )
                path.addArc(
                    Rect(
                        Offset((width / 2).dp.toPx(), (height / 8).dp.toPx()),
                        (-((height / 8) * cos(40f))).dp.toPx()
                    ), -180f, 180f
                )
                path.lineTo(
                    ((width / 2) - ((height / 8) * cos(40f))).dp.toPx(),
                    (((7 * height) / 8) - ((height / 8) * sin(40f))).dp.toPx()
                )
                path.addArc(
                    Rect(
                        Offset((width / 2).dp.toPx(), ((7 * height) / 8).dp.toPx()),
                        (height / 11.42857).dp.toPx()
                    ), 0f, 245f
                )
                path.lineTo(
                    ((width / 2) - ((height / 11.42857) * cos(20f))).dp.toPx(),
                    (height / 8).dp.toPx()
                )
                var posy = ((height / 8) + (strokeWidth / 2)).dp.toPx()
                val posx = ((width / 2) - ((height / 11.42857) * cos(20f))).dp.toPx()
                while (posy < ((height / 8) * 6).dp.toPx()) {
                    path.moveTo(posx, posy)
                    path.lineTo(posx + (height / 40).dp.toPx(), posy)
                    posy += (height / 13.33333).dp.toPx()
                }
                drawPath(path, lineColor, style = Stroke(width = strokeWidth.dp.toPx()))
            }
        }
    ){
        val temptoLine = temperature / 2.77f
        val brush = Brush.verticalGradient(
            colorStops = arrayOf(
                1f - temptoLine/9 to Color.Transparent,
                0.0f to Color.Red,
                0.5f to Color.Cyan,
                1f to Color.Blue
            )
        )
        val path = Path()
        val path2 = Path()
        path.addArc(Rect(Offset((width / 2).dp.toPx(), ((7 * height) / 8).dp.toPx()), (height / 11.42857).dp.toPx()), 0f, 360f)
        path2.addRect(Rect(topLeft = Offset(((width / 2) - ((height / 11.42857) * cos(20f))).dp.toPx(), (height / 8).dp.toPx()), bottomRight = Offset(((width / 2) + ((height / 11.42857) * cos(20f))).dp.toPx(), (((7 * height) / 8) - ((height / 11.42857) * sin(20f))).dp.toPx())))
        path.addArc(Rect(Offset((width/2).dp.toPx(), (height / 8).dp.toPx()), (((height / 11.42857) * cos(20f))).dp.toPx()), -180f, 180f)
        val pathF = path.or(path2)
        drawPath(pathF, brush)
    }
}

@Preview
@Composable
fun PreviewTermometer() {
    SubmarinoTheme (darkTheme = false){
        Termometer(23f)
    }
}