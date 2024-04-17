package com.example.submarino.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.submarino.R
import com.example.submarino.ui.theme.SubmarinoTheme

@Composable
fun InitialScreen(
    onClickInitialButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ){
        Text(
            text = "Sistema De Control Submarino Medidor De " +
                "Calidad del agua",
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        val paint  =
            if (isSystemInDarkTheme())
            {
                R.drawable.logo3
            }
            else{
                R.drawable.logo2
            }
        Image(
            painter = painterResource(id = paint),
            contentDescription = null,
            modifier = modifier
                .height(400.dp)
                .width(400.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Button(onClick = onClickInitialButton) {
                Text(text = "Iniciar")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Creditos")
            }
        }
    }
}

@Preview
@Composable
fun PreviewInitialScreen() {
    SubmarinoTheme (darkTheme = true){
        InitialScreen({})
    }
}