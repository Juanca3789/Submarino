package com.example.submarino

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.submarino.ui.components.PHMeter
import com.example.submarino.ui.components.TopBar
import com.example.submarino.ui.theme.SubmarinoTheme

@Composable
fun MonitorScreen(
    phValue: Double,
    tdsValue: Double,
    tssValue: Double,
    topBarAction: () -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold (
        topBar = { TopBar(title = "Monitor de Datos", buttonText = "Control", buttonAction = topBarAction) },
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .padding(it)
        ){

            Text(text = "Valor de TDS: " + tdsValue)
            Text(text = "Valor de TSS: " + tssValue)
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(12.dp))
                    .padding(6.dp)
            ){
                PHMeter(phValue = phValue)
                Text(text = "Valor de PH: $phValue")
            }
        }
    }
}

@Preview
@Composable
fun PreviewMonitorScreen() {
    SubmarinoTheme {
        MonitorScreen(phValue = 7.0, tdsValue = 3.0, tssValue = 4.0, topBarAction = {})
    }
}