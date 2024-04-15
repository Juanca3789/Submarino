package com.example.submarino.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
fun Termometer(temperature: Float, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier
        .height(400.dp)
        .width(300.dp)
        .drawWithCache {
            onDrawWithContent {
                drawContent()
                val path = Path()
                path.addArc(Rect(Offset(150.dp.toPx(), 350.dp.toPx()), 50.dp.toPx()), -50f, 280f)
                path.lineTo((150 + (50 * cos(40f))).dp.toPx(), 50.dp.toPx())
                path.addArc(Rect(Offset(150.dp.toPx(), 50.dp.toPx()), (-(50 * cos(40f))).dp.toPx()), -180f, 180f)
                path.lineTo((150 - (50 * cos(40f))).dp.toPx(), (350 - (50 * sin(40f))).dp.toPx())
                path.addArc(Rect(Offset(150.dp.toPx(), 350.dp.toPx()), 35.dp.toPx()), 0f, 245f)
                path.lineTo((150 - (35 * cos(20f))).dp.toPx(), 50.dp.toPx())
                var posy = 51.5f.dp.toPx()
                val posx = (150 - (35 * cos(20f))).dp.toPx()
                while (posy < (50 * 6).dp.toPx()){
                    path.moveTo(posx, posy)
                    path.lineTo(posx + 10.dp.toPx(), posy)
                    posy += 30.dp.toPx()
                }
                drawPath(path, Color.Black, style = Stroke(width = 3.dp.toPx()))
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
        path.addArc(Rect(Offset(150.dp.toPx(), 350.dp.toPx()), 35.dp.toPx()), 0f, 360f)
        path2.addRect(Rect(topLeft = Offset((150 - (35 * cos(20f))).dp.toPx(), 50f.dp.toPx()), bottomRight = Offset((150 + (35 * cos(20f))).dp.toPx(), (350 - (35 * sin(20f))).dp.toPx())))
        path.addArc(Rect(Offset(150.dp.toPx(), 50.dp.toPx()), ((35 * cos(20f))).dp.toPx()), -180f, 180f)
        val pathF = path.or(path2)
        drawPath(pathF, brush)
    }
}

@Preview
@Composable
fun PreviewTermometer() {
    SubmarinoTheme {
        Termometer(23f)
    }
}