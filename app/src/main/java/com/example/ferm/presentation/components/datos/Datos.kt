package com.example.ferm.presentation.components.datos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Datos(
    modifier: Modifier,
    onAgregar: () -> Unit,
    onQuitarPrimero: () -> Unit,
    onTabla: () -> Unit,
    navController: NavController
) {
    Box(modifier = modifier
        .fillMaxSize()
        .drawBehind {
            val strokeWidth = 4.dp.toPx()
            val x = size.width - strokeWidth / 2
            drawLine(
                color = Color.Black,
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = strokeWidth
            )
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageRelog()

            Spacer(Modifier.height(8.dp))

            Historial(onTabla = onTabla, navController = navController)

            Spacer(Modifier.height(8.dp))

            EstadosConteo()

            Spacer(Modifier.height(4.dp))

            Botones(
                modifier = Modifier,
                onAgregar = onAgregar,
                onQuitarPrimero = onQuitarPrimero
            )
        }
    }
}