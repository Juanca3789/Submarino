package com.example.submarino

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.submarino.ui.theme.SubmarinoTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
class MainActivity : ComponentActivity() {
    private val REQUEST_ENABLE_BT = 1
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter

    //FUNCION PRINCIPAL
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if(Build.VERSION.SDK_INT > 30){
                    val permission = arrayOf(Manifest.permission.BLUETOOTH_CONNECT)
                    ActivityCompat.requestPermissions(this, permission, REQUEST_ENABLE_BT)
                }
                else{
                    val permission = arrayOf(Manifest.permission.BLUETOOTH_ADMIN)
                    ActivityCompat.requestPermissions(this, permission, REQUEST_ENABLE_BT)
                }
            }
            bluetoothManager = getSystemService(BluetoothManager::class.java)
            bluetoothAdapter = bluetoothManager.adapter
            if (!bluetoothAdapter.isEnabled) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
            SubmarinoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray
                ) {
                    Radar(size = 400f)
                }
            }
        }
    }
}


@Composable
fun Radar(modifier: Modifier = Modifier, size: Float) {
    val angular = 0.0
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row (verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.Gray)
                .padding(4.dp)
        ) {
            Text(
                text = "Sistema Control Submarino",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Button(onClick = { /*TODO*/ }) {
                Text(
                    text = "Conectar"
                )
            }
        }
        Slider(
            value = sliderPosition / 360f,
            onValueChange = { sliderPosition = it * 360f}
        )
        Text(text = sliderPosition.toString())
        Canvas(modifier = modifier
            .height(size.dp)
            .width(size.dp)
            .background(color = Color.Black)
            .drawWithCache {
                onDrawBehind {
                    val center = Offset(size.dp.toPx() / 2f, size.dp.toPx() / 2f)
                    val maxRad = ((size - 2f) / 2f)
                    var p = 0f
                    while (p <= maxRad) {
                        drawCircle(
                            color = Color.Green,
                            radius = p.dp.toPx(),
                            style = Stroke(width = 1.dp.toPx())
                        )
                        p += (maxRad / 3f)
                    }
                    var a = 0.0
                    while (a < 2 * PI) {
                        val destino = Offset(
                            (center.x + (maxRad.dp.toPx() * cos(a))).toFloat(),
                            (center.y - (maxRad.dp.toPx() * sin(a))).toFloat()
                        )
                        drawLine(
                            color = Color.Green,
                            center,
                            destino,
                            strokeWidth = 1.dp.toPx()
                        )
                        a += (PI / 6)
                    }
                }
            }
            .graphicsLayer {
                this.rotationZ = 360 - sliderPosition
            },
            onDraw = {
                val center = Offset(size.dp.toPx() / 2f, size.dp.toPx() / 2f)
                val maxRad = ((size - 2f) / 2f)
                var a = angular - (PI / 3)
                while (a < angular) {
                    val destino = Offset(
                        (center.x + (maxRad.dp.toPx() * cos(a))).toFloat(),
                        (center.y - (maxRad.dp.toPx() * sin(a))).toFloat()
                    )
                    drawLine(
                        color = Color(
                            0,
                            255,
                            0,
                            alpha = (200 - (3.33 * ((angular - a) * (180.0 / PI)))).toInt()
                        ), center, destino, strokeWidth = 1.dp.toPx()
                    )
                    a += (PI / 180)
                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SubmarinoTheme {
        Radar(size= 400f)
    }
}