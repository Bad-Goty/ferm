package com.example.ferm.presentation.components.camara

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Camara(
    modifier: Modifier,
    filas: Int,
    columnas: Int,
    activosNums: List<Int>
) {
    val totalCeldas = filas * columnas
    val celdas: List<Int?> = activosNums.take(totalCeldas) + List((totalCeldas - activosNums.size).coerceAtLeast(0)) { null }

    Box(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            for (col in 0 until columnas) {
                Column {
                    for (row in 0 until filas) {
                        val index = col * filas + row // tu patrón por columnas
                        val value = celdas.getOrNull(index)

                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .size(150.dp)
                                .background(if (value != null) Color(0xFF1EB980) else Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            if (value != null) {
                                Text(
                                    text = value.toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
