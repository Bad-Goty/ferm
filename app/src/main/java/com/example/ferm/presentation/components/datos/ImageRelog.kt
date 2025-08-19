package com.example.ferm.presentation.components.datos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ferm.R
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun ImageRelog(modifier: Modifier = Modifier) {
    var currentTime by remember { mutableStateOf(getCurrentTime()) }

    // Efecto que actualiza la hora cada segundo
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = getCurrentTime()
            delay(1000) // actualiza cada 1 segundo
        }
    }

    Column(modifier = Modifier.height(120.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(120.dp) //tablet 120.dp
            )

            Text(
                text = currentTime.toString(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 80.sp
            )
        }
        HorizontalDivider(color = Color.Black, thickness = 2.dp)
    }

}

// Función que obtiene la hora actual del sistema en formato HH:mm
fun getCurrentTime(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return LocalTime.now().format(formatter)
}