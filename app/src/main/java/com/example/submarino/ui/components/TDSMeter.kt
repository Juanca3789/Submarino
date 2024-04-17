package com.example.submarino.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.submarino.R

@Composable
fun TDSMeter(TDSValue: Float, maxWidth: Float, modifier: Modifier = Modifier) {
    val scale = TDSValue/1024
    Image (
        painter = painterResource(id = R.drawable.piedra),
        contentDescription = null,
        modifier = modifier
            .width(maxWidth.dp)
            .height((maxWidth * (80f/112f)).dp)
            .graphicsLayer {
                this.scaleX = scale
                this.scaleY = scale
            }
    )
}

@Preview
@Composable
fun PreviewTDSMeter() {
    TDSMeter(512f, 112f)
}