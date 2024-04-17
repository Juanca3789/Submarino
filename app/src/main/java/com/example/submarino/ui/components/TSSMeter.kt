package com.example.submarino.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.submarino.ui.theme.SubmarinoTheme
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun TSSMeter(turbidity: Double, height: Float = 300f,lineWidth: Float = 3f,modifier: Modifier = Modifier) {
    val width = (height * 0.83333f)
    val lineColor = MaterialTheme.colorScheme.onBackground
    Canvas(
        modifier = modifier
            .height(height.dp)
            .width(width.dp)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    val path = Path()
                    path.moveTo(lineWidth.dp.toPx(), lineWidth.dp.toPx())
                    path.lineTo((width - lineWidth).dp.toPx(), lineWidth.dp.toPx())
                    path.lineTo((width - (height / 6)).dp.toPx(), (height - lineWidth).dp.toPx())
                    path.lineTo((height / 6).dp.toPx(), (height - lineWidth).dp.toPx())
                    path.close()
                    drawPath(path, lineColor, style = Stroke(width = lineWidth.dp.toPx()))
                    val points = mutableListOf<Offset>()
                    var i = (width / 14.705)
                    while (i < (width - (width / 14.705))) {
                        val x = i
                        val y = (10 * sin((((2 * (i * PI) / 180))))) + (height / 3.75)
                        points.add(Offset(x.dp.toPx(), y.dp.toPx()))
                        i += 1
                    }
                    drawPoints(
                        points,
                        PointMode.Points,
                        lineColor,
                        strokeWidth = lineWidth.dp.toPx()
                    )
                }
            }
    ) {
        val colors = arrayOf(
            Color(0xFF5B1F00),
            Color(0XFF713112),
            Color(0XFF874421),
            Color(0XFFB4764F),
            Color(0XFFE6B68B),
            Color(0XFFFFFFFF),
            Color(0xFF9FFFFF),
            Color(0xFF71FFFF),
            Color(0XFF00FFFF),
            Color(0XFF00E5E5),
            Color(0XFF00CBCC)
        )
        val path = Path()
        var i = (width / 14.705)
        path.moveTo(i.dp.toPx(), ((10 * sin((((2 * (i * PI) / 180))))) + (height / 3.75)).dp.toPx())
        while (i < (width - (width / 14.705))) {
            path.lineTo(i.dp.toPx(), ((10 * sin((((2 * (i * PI) / 180))))) + (height / 3.75)).dp.toPx())
            i += 1
        }
        path.lineTo((width - (height / 6)).dp.toPx(), (height - lineWidth).dp.toPx())
        path.lineTo((height / 6).dp.toPx(), (height - lineWidth).dp.toPx())
        path.close()
        val resolution = 1024
        var index = 6
        when (turbidity.toInt()) {
            in 0..(resolution / 11) -> index = 0
            in (resolution / 11)..<((2 * resolution) / 11) -> index = 1
            in ((2 * resolution) / 11)..<((3 * resolution) / 11) -> index = 2
            in ((3 * resolution) / 11)..<((4 * resolution) / 11) -> index = 3
            in ((4 * resolution) / 11)..<((5 * resolution) / 11) -> index = 4
            in ((5 * resolution) / 11)..<((6 * resolution) / 11) -> index = 5
            in ((6 * resolution) / 11)..<((7 * resolution) / 11) -> index = 6
            in ((7 * resolution) / 11)..<((8 * resolution) / 11) -> index = 7
            in ((8 * resolution) / 11)..<((9 * resolution) / 11) -> index = 8
            in ((9 * resolution) / 11)..<((10 * resolution) / 11) -> index = 9
            in ((10 * resolution) / 11)..<((11 * resolution) / 11) -> index = 10
        }
        drawPath(path, colors[index])
    }
}

@Preview
@Composable
fun PreviewTSSMeter() {
    SubmarinoTheme (darkTheme = false){
        TSSMeter(turbidity = 800.0)
    }
}