package com.example.ferm.presentation.components.datos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ferm.presentation.viewmodel.CarritosViewModel

@Composable
fun Historial(
    modifier: Modifier = Modifier,
    onTabla: () -> Unit,
    viewModel: CarritosViewModel = hiltViewModel(),
    navController: NavController
) {
    val items by viewModel.carrosFinalizadosHoyTop30.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)//260
            .padding(horizontal = 8.dp)
            .background(Color.White)
            .border(width = 2.dp, color = Color.Black)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row( modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround

            ) {
                Text(
                    "Historial",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                )

                IconButton(
                    onClick = {onTabla()},
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            }


            if (items.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Sin registros hoy", color = Color.Gray)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    items.forEach { it ->
                        Text(
                            text = "Carro ${it.carritoNum} ingresó a las ${it.horaEntrada} salió a las ${it.horaSalida} (${it.estadoSalida})",
                            color = Color.Black,
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                .fillMaxWidth()
                                .background(if (it.estadoSalida == "bien") Color(0x7C85EC6C) else Color(
                                    0xDFF5759E
                                )
                                )
                                .drawBehind {
                                    val strokeWidth = 2.dp.toPx()
                                    val y = size.height - strokeWidth / 2
                                    drawLine(
                                        color = Color.Black,
                                        start = Offset(0f, y),
                                        end = Offset(size.width, y),
                                        strokeWidth = strokeWidth
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}
