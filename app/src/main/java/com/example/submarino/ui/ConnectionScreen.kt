package com.example.submarino.ui

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.submarino.R
import com.example.submarino.ui.components.TopBar
import com.example.submarino.ui.theme.SubmarinoTheme

@Composable
fun ConnectionScreen(devices: Set<BluetoothDevice>?, connectionFunction: (BluetoothDevice) -> Unit, topBarAction: () -> Unit, reloadFunction: () -> Unit, modifier: Modifier = Modifier) {
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
                Text(text = "Reload")
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
fun CardBTDevice(device: BluetoothDevice, onClickButtonConnect: (BluetoothDevice) -> Unit, modifier: Modifier = Modifier) {
   Row (
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.SpaceBetween,
       modifier= Modifier
           .fillMaxWidth()
           .height(80.dp)
   ){
       Image(
           painter = painterResource(id = R.drawable.logo),
           contentDescription = null,
           modifier= modifier
               .size(70.dp)
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
                    text = "Name: " + device.name
                )
                Text(
                    text = "Adress: " + device.address
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
        ConnectionScreen(setOf(),{},{},{})
    }
}