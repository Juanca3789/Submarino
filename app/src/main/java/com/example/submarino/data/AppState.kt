package com.example.submarino.data

import android.bluetooth.BluetoothDevice
import kotlin.math.cos
import kotlin.math.sin

data class Punto(
    val angle: Float,
    val distance: Float,
    val x: Float = distance * cos(angle),
    val y: Float = distance * sin(angle)
)
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
    val objects: List<Punto> = listOf()
)