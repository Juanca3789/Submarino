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
import com.example.submarino.data.Punto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.nio.charset.Charset
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
    //Bluetooth
    private val REQUEST_ENABLE_BT = 1
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var connectedDevice: BluetoothDevice
    private val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private lateinit var connectionSocket: BluetoothSocket
    private val buffer = ByteArray(1024)
    private var numBytes: Int = 0
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
                val perm = arrayOf(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN)
                ActivityCompat.requestPermissions(
                    context.findActivity(),
                    perm,
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

    fun connectToMicrocontroller(context: Context) : Boolean{
        val BTTAG = "ERROR CONNECTING"
        try {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
                ) {
                if(Build.VERSION.SDK_INT < 31){
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.BLUETOOTH_ADMIN
                        ) != PackageManager.PERMISSION_GRANTED) {
                        Log.d(BTTAG, "Admin")
                        _uiState.update { currentState ->
                            currentState.copy(
                                openFailConnectionDialog = true
                            )
                        }
                        return false
                    }
                }
                else {
                    Log.d(BTTAG, "Connect: ")
                    _uiState.update { currentState ->
                        currentState.copy(
                            openFailConnectionDialog = true
                        )
                    }
                    return false
                }
            }
            bluetoothAdapter.cancelDiscovery()
            connectionSocket = connectedDevice.createRfcommSocketToServiceRecord(MY_UUID)
            connectionSocket.connect()
            Log.d(BTTAG, "Connectado con exito")
            return true
        } catch (e: IOException) {
            Log.e(BTTAG, "Fallo de conexión")
            _uiState.update { currentState->
                currentState.copy(
                    openFailConnectionDialog = true
                )
            }
            return false
        }
    }

    fun sendData(data: String){
        viewModelScope.launch (context =  Dispatchers.IO) {
            val TAG = "DATA SENDER"
            try {
                connectionSocket.outputStream.write(data.encodeToByteArray())
                Log.d(TAG, data)
            } catch (e: IOException) {
                Log.e(TAG, "Error: " + e.message)
            }
        }
    }

    fun readData(){
        viewModelScope.launch(context = Dispatchers.IO) {
            while (true){
                if(connectionSocket.inputStream.available() > 0){
                    val num =
                        try {
                            connectionSocket.inputStream.read(buffer, numBytes, buffer.size - numBytes)
                        }
                        catch (e: IOException){
                            Log.d("Disconnected", e.toString())
                        }
                    numBytes += num
                    if(buffer[numBytes - 1].toInt() == 10){
                        val auxBuffer = ByteArray(numBytes)
                        for (i in 0..<numBytes){
                            auxBuffer[i] = buffer[i]
                            buffer[i] = 0
                        }
                        val readMsg = auxBuffer.toString(Charset.defaultCharset())
                        Log.d("DATA RECEIVER", readMsg)
                        val received = readMsg.split('=', ',')
                        _uiState.update { currentState->
                            when(received[0]) {
                                "RA" -> {
                                    val actList = currentState.objects.toMutableList()
                                    actList.add(
                                        Punto(
                                            angle= try{
                                                received[1].toFloat()
                                            }
                                            catch (e: NumberFormatException){
                                                0f
                                            },
                                            distance = try{
                                                received[2].toFloat()
                                            }
                                            catch (e: NumberFormatException){
                                                0f
                                            }
                                        )
                                    )
                                    currentState.copy(
                                        objects = actList
                                    )
                                }
                                "RU" -> {
                                    val actList = currentState.objects.toMutableList()
                                    actList.remove(actList.find {it.angle == received[1].toFloat()})
                                    actList.add(
                                        Punto(
                                            angle= try{
                                                        received[1].toFloat()
                                                    }
                                                    catch (e: NumberFormatException){
                                                        0f
                                                    },
                                            distance = try{
                                                        received[2].toFloat()
                                                    }
                                                    catch (e: NumberFormatException){
                                                        0f
                                                    }
                                        )
                                    )
                                    currentState.copy(
                                        objects = actList
                                    )
                                }
                                "RD" -> {
                                    val actList = currentState.objects.toMutableList()
                                    actList.remove(
                                        actList.find {
                                            it.angle == try {
                                                received[1].toFloat()
                                            }catch (e: NumberFormatException){
                                                0f
                                            }
                                        }
                                    )
                                    currentState.copy(
                                        objects = actList
                                    )
                                }
                                "TSS" -> currentState.copy(
                                    tss = try {
                                        received[1].toDouble()
                                    }
                                    catch (e: NumberFormatException){
                                        0.0
                                    }
                                )
                                "TDS" -> currentState.copy(
                                    tds = try {
                                        received[1].toDouble()
                                    }
                                    catch (e: NumberFormatException){
                                        0.0
                                    }
                                )
                                "PH" -> currentState.copy(
                                    ph = try {
                                        received[1].toDouble()
                                    }
                                    catch (e: NumberFormatException){
                                        0.0
                                    }
                                )
                                "TMP" -> currentState.copy(
                                    temperature = try {
                                        received[1].toDouble()
                                    }
                                    catch (e: NumberFormatException){
                                        0.0
                                    }
                                )
                                else -> currentState.copy(
                                    receivedData = readMsg
                                )
                            }
                        }
                        numBytes = 0
                    }
                }
            }
        }
    }

    fun setSpeed(speed: Float){
        _uiState.update {currentState ->
            currentState.copy(
                velocity = speed
            )
        }
        val tmpVelocidad = (4095 * _uiState.value.velocity).toInt()
        var strImp = "V"
        var exp = 1000
        while (exp > 1){
            exp = exp / 10
            if(tmpVelocidad < exp) {
                strImp += '0'
            }
        }
        strImp += tmpVelocidad.toString()
        this.sendData(strImp)
    }

    suspend fun radarSpin() {
        var dir = 1
        while (true) {
            _uiState.update {currentState ->
                val actPos = currentState.radarPosition
                if(actPos.toInt() == 359 || actPos.toInt() == 0) {
                    dir *= -1
                }
                currentState.copy(
                    radarPosition = (actPos + dir) % 360
                )
            }
            delay(14)
        }
    }

    fun closeConnectionRequest() {
        _uiState.update { currentState ->
            currentState.copy(
                openFailConnectionDialog = false
            )
        }
    }


}