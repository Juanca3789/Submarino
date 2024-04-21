package com.example.submarino.data

import android.app.AlertDialog
import android.bluetooth.BluetoothDevice
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Punto(
    val angle: Float,
    val distance: Float,
) {
    val x: Float = distance * cos((-angle * PI).toFloat()/180f)
    val y: Float = distance * sin((-angle * PI).toFloat()/180f)
}

data class AppState (
    val tds: Double = 0.0,
    val tss:  Double = 0.0,
    val ph: Double = 0.0,
    val temperature: Double = 0.0,
    val radarPosition: Double = 0.0,
    val radarDistance: Double = 0.0,
    val pairedDevices: Set<BluetoothDevice>? = null,
    val receivedData: String = "",
    val velocity: Float = 0.0f,
    val objects: List<Punto> = listOf(),
    val openFailConnectionDialog: Boolean = false
)