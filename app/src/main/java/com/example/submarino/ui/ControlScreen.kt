package com.example.submarino.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.KeyboardDoubleArrowDown
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.submarino.data.Punto
import com.example.submarino.ui.components.Radar
import com.example.submarino.ui.components.TopBar
import com.example.submarino.ui.theme.SubmarinoTheme


@Composable
fun ControlScreen(
    buttonFunctions: List<() -> Unit>,
    topBarAction: () -> Unit,
    dataText: String,
    speedValue: Float,
    setSpeed: (Float) -> Unit,
    radPosition: Float = 0f,
    objectsRadar: List<Punto>,
    modifier: Modifier = Modifier) {
    Scaffold (
        topBar = { TopBar(title = "Control De Movimiento", buttonText = "Monitor", buttonAction = topBarAction)},
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
            BoxWithConstraints (
                modifier= modifier
                    .fillMaxWidth()
            ){
                if(maxWidth >= 380f.dp){
                    Radar(position = radPosition, objects = objectsRadar)
                }
                else{
                    Radar(size= maxWidth.value, position = radPosition, objects = objectsRadar)
                }
            }
            Controllers(buttonFunctions, dataText, speedValue, setSpeed)
        }
    }
}

@Composable
fun Controllers(
    functions: List<() -> Unit>,
    dataText : String,
    speedValue: Float,
    setSpeed: (Float) -> Unit,
    modifier: Modifier = Modifier) {
    Column (
        verticalArrangement = Arrangement.SpaceAround
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Button(onClick = functions[0]) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Undo, contentDescription = null)
            }
            Text(
                text = "_$dataText",
                color = MaterialTheme.colorScheme.onBackground
            )
            Button(onClick = functions[8]) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Redo, contentDescription = null)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Button(onClick = functions[1]) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
                Column {
                    Button(onClick = functions[2]) {
                        Icon(imageVector = Icons.Filled.ArrowUpward, contentDescription = null)

                    }
                    Button(onClick = functions[3]) {
                        Icon(imageVector = Icons.Filled.Stop, contentDescription = null)
                    }
                    Button(onClick = functions[4]) {
                        Icon(imageVector = Icons.Filled.ArrowDownward, contentDescription = null)
                    }
                }
                Button(onClick = functions[5]) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
            Column {
                Button(onClick = functions[6]) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardDoubleArrowUp,
                        contentDescription = null
                    )
                }
                Button(onClick = functions[7]) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardDoubleArrowDown,
                        contentDescription = null
                    )
                }
            }
        }
        Slider(value = speedValue, onValueChange = setSpeed)
    }
}

@Preview
@Composable
fun PreviewControlScreen() {
    SubmarinoTheme (darkTheme = false){
        ControlScreen(listOf({}),{}, "", 0f, {}, objectsRadar = listOf(Punto(30f, 20f)))
    }
}