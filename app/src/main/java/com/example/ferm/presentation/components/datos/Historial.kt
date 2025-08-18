package com.example.ferm.presentation.components.datos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Historial(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(horizontal = 8.dp)
            .background(Color.White)
            .border(width = 2.dp, color = Color.Black)

    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                "Historial",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth().background(Color.Black),
                textAlign = TextAlign.Center
            )

            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                for (i in 1..30) {
                    Text(
                        text = "Carro $i ingreso a las 10:48:00 salio a las 10:50:00",
                        color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                            .fillMaxWidth()
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