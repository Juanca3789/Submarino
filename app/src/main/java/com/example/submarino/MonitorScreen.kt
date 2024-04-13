package com.example.submarino

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.submarino.ui.components.TopBar

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
            Text(text = "Valor de PH: " + phValue)
            Text(text = "Valor de TDS: " + tdsValue)
            Text(text = "Valor de TSS: " + tssValue)
        }
    }
}

