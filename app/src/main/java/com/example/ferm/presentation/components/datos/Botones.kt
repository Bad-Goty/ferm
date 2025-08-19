package com.example.ferm.presentation.components.datos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Botones(
    modifier: Modifier = Modifier,
    onAgregar: () -> Unit,
    onQuitarPrimero: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        Button(
            onClick = onAgregar,
            modifier = Modifier.fillMaxWidth().height(48.dp).weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.Black
            )
        ) { Text("Agregar Carrito", fontSize = 20.sp, fontWeight = FontWeight.Bold) }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = onQuitarPrimero,
            modifier = Modifier.fillMaxWidth().height(48.dp).weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEA6774),
                contentColor = Color.Black
            )
        ) { Text("Sacar Carrito", fontSize = 20.sp, fontWeight = FontWeight.Bold) }
    }
}

