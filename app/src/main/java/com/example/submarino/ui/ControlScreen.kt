package com.example.submarino.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.submarino.ui.components.Radar
import com.example.submarino.ui.components.TopBar
import com.example.submarino.ui.theme.SubmarinoTheme

@Composable
fun ControlScreen(modifier: Modifier = Modifier) {
    Scaffold (
        topBar = { TopBar(title = "Control De Movimiento", buttonText = "Monitor")},
        modifier = Modifier
            .fillMaxSize()
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ){
            Radar()
            Controllers()
            Slider(value = 0f, onValueChange = {})
        }
    }
}

@Composable
fun Controllers(modifier: Modifier = Modifier) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
                .fillMaxWidth()
    ){
        Button(onClick = { /*TODO*/ }) {
            Text(text = "GI")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "L")
        }
        Column {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "U")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "F")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "D")
            }
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "R")
        }
        Column {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "A")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "S")
            }
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "GD")
        }
    }
}
@Preview
@Composable
fun PreviewControlScreen() {
    SubmarinoTheme (darkTheme = false){
        ControlScreen()
    }
}