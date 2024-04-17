package com.example.submarino.ui

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.BluetoothDisabled
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import com.example.submarino.ui.components.TopBar
import com.example.submarino.ui.theme.SubmarinoTheme
import java.net.CacheRequest

@Composable
fun ConnectionScreen(
    devices: Set<BluetoothDevice>?,
    connectionFunction: (BluetoothDevice) -> Unit,
    topBarAction: () -> Unit,
    reloadFunction: () -> Unit,
    openAlertDialog: Boolean,
    dismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold (
        topBar = { TopBar(title = "Cónectate Al Submarino", buttonText = "Inicio", buttonAction = topBarAction) },
        modifier = Modifier
            .fillMaxSize()
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier= modifier
                .fillMaxSize()
                .padding(it)
        ){
            DevicesList(
                devices = devices,
                connectionFunction= connectionFunction
            )
            Button(onClick = reloadFunction) {
                Icon(imageVector = Icons.Filled.Refresh, contentDescription = null)
            }
        }
    }
    if(openAlertDialog){
        Dialog(onDismissRequest = dismissRequest) {
            Card {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ){
                    Icon(
                        imageVector = Icons.Filled.BluetoothDisabled,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "Error conectando con dispositivo, esto puede deberse a: "
                    )
                    Text(
                        text = "1. El dispositivo no se encuentra encendido"
                    )
                    Text(
                        text = "2. El dispositivo se encuentra conectado a otro controlador"
                    )
                    Text(
                        text = "3. No se han concedido los permisos bluetooth necesarios"
                    )
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp, bottom = 12.dp)
                ){
                    OutlinedButton(onClick = dismissRequest) {
                        Text(text = "Regresar")
                    }
                }
            }
        }
    }
}

@Composable
fun DevicesList(devices: Set<BluetoothDevice>?, connectionFunction: (BluetoothDevice) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        devices?.forEach{
            item {
                CardBTDevice(device = it, onClickButtonConnect = connectionFunction)
            }
        }
    }
}

@Composable
fun CardBTDevice(
    device: BluetoothDevice,
    onClickButtonConnect: (BluetoothDevice) -> Unit,
    modifier: Modifier = Modifier
) {
   Row (
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.SpaceBetween,
       modifier= Modifier
           .fillMaxWidth()
           .height(80.dp)
           .padding(12.dp)
   ){
       Icon(
           imageVector = Icons.Filled.Bluetooth,
           contentDescription = null,
           modifier = modifier
               .size(50.dp)
       )
       Column (
           verticalArrangement = Arrangement.Center,
           modifier = modifier
               .fillMaxHeight()
       ){
            if (ActivityCompat.checkSelfPermission(
                    LocalContext.current,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Text(
                    text = "Name: " + device.name,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Adress: " + device.address,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
       }
       Button(onClick = { onClickButtonConnect(device) }) {
           Text(text = "Conectar")
       }
   }
}

@Preview
@Composable
fun PreviewConnectionScreen() {
    SubmarinoTheme {
        ConnectionScreen(setOf(),{},{},{}, true, {})
    }
}