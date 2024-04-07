package com.example.submarino.data

import android.bluetooth.BluetoothDevice

data class AppState (
    val tds: Double = 0.0,
    val tss:  Double = 0.0,
    val radarPosition: Double = 0.0,
    val pairedDevices: Set<BluetoothDevice>? = null,
    val receivedData: String = ""
)