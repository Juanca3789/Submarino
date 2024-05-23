package com.example.submarino.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
                contentAlignment = Alignment.Center,
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
    val interactionSource1 = remember { MutableInteractionSource() }
    val interactionSource2 = remember { MutableInteractionSource() }
    val interactionSource3 = remember { MutableInteractionSource() }
    val interactionSource4 = remember { MutableInteractionSource() }
    val interactionSource6 = remember { MutableInteractionSource() }
    val interactionSource7 = remember { MutableInteractionSource() }
    val interactionSource8 = remember { MutableInteractionSource() }
    val interactionSource9 = remember { MutableInteractionSource() }
    val isPressed1 by interactionSource1.collectIsPressedAsState()
    val isPressed2 by interactionSource2.collectIsPressedAsState()
    val isPressed3 by interactionSource3.collectIsPressedAsState()
    val isPressed4 by interactionSource4.collectIsPressedAsState()
    val isPressed6 by interactionSource6.collectIsPressedAsState()
    val isPressed7 by interactionSource7.collectIsPressedAsState()
    val isPressed8 by interactionSource8.collectIsPressedAsState()
    val isPressed9 by interactionSource9.collectIsPressedAsState()
    val antPressed1 = remember { mutableStateOf(false) }
    val antPressed2 = remember { mutableStateOf(false) }
    val antPressed3 = remember { mutableStateOf(false) }
    val antPressed4 = remember { mutableStateOf(false) }
    val antPressed6 = remember { mutableStateOf(false) }
    val antPressed7 = remember { mutableStateOf(false) }
    val antPressed8 = remember { mutableStateOf(false) }
    val antPressed9 = remember { mutableStateOf(false) }
    Column (
        verticalArrangement = Arrangement.SpaceAround
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = {},
                interactionSource = interactionSource1
            ) {
                if (isPressed1 && !antPressed1.value) {
                    antPressed1.value = true
                    functions[0]()
                }
                else if(antPressed1.value && !isPressed1){
                    functions[3]()
                    antPressed1.value = false
                }
                Icon(imageVector = Icons.AutoMirrored.Filled.Undo, contentDescription = null)
            }
            Text(
                text = "_$dataText",
                color = MaterialTheme.colorScheme.onBackground
            )
            Button(
                onClick = {},
                interactionSource = interactionSource2
            ) {
                if (isPressed2 && !antPressed2.value) {
                    antPressed2.value = true
                    functions[8]()
                }
                else if(antPressed2.value && !isPressed2){
                    functions[3]()
                    antPressed2.value = false
                }
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
                Button(
                    onClick = {},
                    interactionSource = interactionSource3
                    ) {
                    if (isPressed3 && !antPressed3.value) {
                        antPressed3.value = true
                        functions[1]()
                    }
                    else if(antPressed3.value && !isPressed3){
                        functions[3]()
                        antPressed3.value = false
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
                Column {
                    Button(
                        onClick = {},
                        interactionSource = interactionSource4
                    ) {
                        if (isPressed4 && !antPressed4.value) {
                            antPressed4.value = true
                            functions[2]()
                        }
                        else if(antPressed4.value && !isPressed4){
                            functions[3]()
                            antPressed4.value = false
                        }
                        Icon(imageVector = Icons.Filled.ArrowUpward, contentDescription = null)

                    }
                    Button(
                        onClick = {functions[3]()}
                    ) {
                        Icon(imageVector = Icons.Filled.Stop, contentDescription = null)
                    }
                    Button(
                        onClick = {},
                        interactionSource = interactionSource6
                    ) {
                        if (isPressed6 && !antPressed6.value) {
                            antPressed6.value = true
                            functions[4]()
                        }
                        else if(antPressed6.value && !isPressed6){
                            functions[3]()
                            antPressed6.value = false
                        }
                        Icon(imageVector = Icons.Filled.ArrowDownward, contentDescription = null)
                    }
                }
                Button(
                    onClick = {},
                    interactionSource = interactionSource7
                ) {
                    if (isPressed7 && !antPressed7.value) {
                        antPressed7.value = true
                        functions[5]()
                    }
                    else if(antPressed7.value && !isPressed7){
                        functions[3]()
                        antPressed7.value = false
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
            Column {
                Button(
                    onClick = {},
                    interactionSource = interactionSource8
                ) {
                    if (isPressed8 && !antPressed8.value) {
                        antPressed8.value = true
                        functions[6]()
                    }
                    else if(antPressed8.value && !isPressed8){
                        functions[3]()
                        antPressed8.value = false
                    }
                    Icon(
                        imageVector = Icons.Filled.KeyboardDoubleArrowUp,
                        contentDescription = null
                    )
                }
                Button(
                    onClick = {},
                    interactionSource = interactionSource9
                ) {
                    if (isPressed9 && !antPressed9.value) {
                        antPressed9.value = true
                        functions[7]()
                    }
                    else if(antPressed9.value && !isPressed9){
                        functions[3]()
                        antPressed9.value = false
                    }
                    Icon(
                        imageVector = Icons.Filled.KeyboardDoubleArrowDown,
                        contentDescription = null
                    )
                }
            }
        }
        Slider(
            value = speedValue,
            onValueChange = setSpeed
        )
    }
}

@Preview
@Composable
fun PreviewControlScreen() {
    SubmarinoTheme (darkTheme = false){
        ControlScreen(listOf({}),{}, "", 0f, {}, objectsRadar = listOf(Punto(30f, 20f)))
    }
}