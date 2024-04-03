package com.example.submarino

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.submarino.ui.ConnectionScreen
import com.example.submarino.ui.ControlScreen
import com.example.submarino.ui.InitialScreen
import com.example.submarino.ui.theme.SubmarinoTheme

class MainActivity : ComponentActivity() {
    //FUNCION PRINCIPAL
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /*
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
            */
            SubmarinoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray
                ) {
                    AppSubmarino()
                }
            }
        }
    }
}

enum class SubmarinoScreen {
    Start,
    Connection,
    Control
}

@Composable
fun AppSubmarino(
    viewModel: SubmarinoViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController = rememberNavController()
    ) {
    val uiState by viewModel.uiState.collectAsState()
    NavHost(
        navController = navController ,
        startDestination = SubmarinoScreen.Start.name
    ) {
        composable(route = SubmarinoScreen.Start.name) {
            val context = LocalContext.current
            InitialScreen(
                onClickInitialButton = {
                    onInit(viewModel, navController, context)
                }
            )
        }
        composable(route = SubmarinoScreen.Connection.name) {
            ConnectionScreen(
                connectionFunction = {
                    navController.navigate(SubmarinoScreen.Control.name)
                }
            )
        }
        composable(route = SubmarinoScreen.Control.name) {
            ControlScreen()
        }
    }
}

fun onInit(viewModel: SubmarinoViewModel,
           navController: NavHostController,
           context: Context) {
    viewModel.initBluetooth(context = context)
    navController.navigate(SubmarinoScreen.Connection.name)
}
