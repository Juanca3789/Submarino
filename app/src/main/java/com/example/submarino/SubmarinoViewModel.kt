package com.example.submarino

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submarino.data.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

class SubmarinoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppState())
    val uiState: StateFlow<AppState> = _uiState.asStateFlow()
    private val REQUEST_ENABLE_BT = 1
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var connectedDevice: BluetoothDevice
    private val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private lateinit var connectionSocket: BluetoothSocket

    fun setConnectedDevice(device: BluetoothDevice) {
        connectedDevice = device
    }

    fun initBluetooth(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT > 30) {
                val permission = arrayOf(Manifest.permission.BLUETOOTH_CONNECT)
                ActivityCompat.requestPermissions(
                    context.findActivity(),
                    permission,
                    REQUEST_ENABLE_BT
                )
            } else {
                val permission = arrayOf(Manifest.permission.BLUETOOTH_ADMIN)
                ActivityCompat.requestPermissions(
                    context.findActivity(),
                    permission,
                    REQUEST_ENABLE_BT
                )
            }
        }
        bluetoothManager = context.getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager.adapter
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            context.findActivity().startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        } else {
            _uiState.update {currentState ->
                currentState.copy(
                    pairedDevices = bluetoothAdapter.bondedDevices
                )
            }
        }
    }

    fun connectToMicrocontroller(context: Context) {
        val BTTAG = "ERROR CONNECTING"
        try {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(BTTAG, "connectToMicrocontroller: ")
                return
            }
            bluetoothAdapter.cancelDiscovery()
            connectionSocket = connectedDevice.createRfcommSocketToServiceRecord(MY_UUID)
            connectionSocket.connect()
            Log.d(BTTAG, "Connectado con exito")
        } catch (e: IOException) {
            Log.e(BTTAG, "Fallo de conexión")
        }
    }

    fun sendData(data: String){
        val TAG = "DATA SENDER"
        try {
            connectionSocket.outputStream.write(data.encodeToByteArray())
            Log.d(TAG, data)
        }
        catch (e: IOException){
            Log.e(TAG, "Error: " + e.message)
        }
    }

    fun readData(){
        viewModelScope.launch {
            val buffer = ByteArray(1024)
            while (true){
                if (connectionSocket.inputStream.available() > 0){
                    try {
                        connectionSocket.inputStream.read(buffer)
                        val readMsg = buffer.toString()
                        _uiState.update {currentState ->
                            currentState.copy(
                                receivedData = readMsg
                            )
                        }
                    }catch (e: IOException){
                        Log.d("Disconnected", "readData: ")
                    }
                }
            }
        }
    }

}