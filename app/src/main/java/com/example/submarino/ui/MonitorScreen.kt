package com.example.submarino.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.submarino.ui.components.PHMeter
import com.example.submarino.ui.components.TDSMeter
import com.example.submarino.ui.components.TSSMeter
import com.example.submarino.ui.components.Termometer
import com.example.submarino.ui.components.TopBar
import com.example.submarino.ui.theme.SubmarinoTheme

@Composable
fun MonitorScreen(
    phValue: Double,
    tdsValue: Double,
    tssValue: Double,
    tempValue: Double,
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
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.primaryContainer,
                        RoundedCornerShape(12.dp)
                    )
                    .padding(vertical = 15.dp)
            ){
                PHMeter(phValue = phValue)
                Text(text = "Valor de PH: $phValue")
            }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .height(330.dp)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.primaryContainer,
                        RoundedCornerShape(12.dp)
                    )
            ){
                Termometer(temperature = tempValue.toFloat(), height = 300f)
                Text(text = "Valor de Temperatura: $tempValue")
            }

            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .fillMaxWidth(0.5f)
                        .height(250.dp)
                        .border(
                            2.dp,
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 15.dp)
                ) {
                    TSSMeter(turbidity = tssValue, height = 200f)
                    Text(text = "Valor de TSS: $tssValue")
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .border(
                            2.dp,
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 15.dp)
                ) {
                    TDSMeter(TDSValue = tdsValue.toFloat(), maxWidth = 200f)
                    Text(text = "Valor de TDS: $tdsValue")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMonitorScreen() {
    SubmarinoTheme (darkTheme = false){
        MonitorScreen(phValue = 7.0, tdsValue = 600.0, tssValue = 800.0, tempValue = 8.0,topBarAction = {})
    }
}