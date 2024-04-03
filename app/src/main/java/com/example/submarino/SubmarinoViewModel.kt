package com.example.submarino

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.example.submarino.data.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

class SubmarinoViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(AppState())
    val uiState : StateFlow<AppState> = _uiState.asStateFlow()
    private val REQUEST_ENABLE_BT = 1
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter

    fun initBluetooth(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if(Build.VERSION.SDK_INT > 30){
                val permission = arrayOf(Manifest.permission.BLUETOOTH_CONNECT)
                ActivityCompat.requestPermissions(context.findActivity(), permission, REQUEST_ENABLE_BT)
            }
            else{
                val permission = arrayOf(Manifest.permission.BLUETOOTH_ADMIN)
                ActivityCompat.requestPermissions(context.findActivity(), permission, REQUEST_ENABLE_BT)
            }
        }
        bluetoothManager = context.getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager.adapter
        if(!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            context.findActivity().startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
    }

}