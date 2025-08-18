package com.example.ferm.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ferm.data.entity.CarritoEntity
import java.util.Date

@Composable
fun CarritosTableDialog(
    carritos: List<CarritoEntity>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Cerrar") }
        },
        title = { Text("Carritos (DESC por ID)") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Encabezados
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HeaderCell("ID", 0.8f)
                    HeaderCell("Num", 0.8f)
                    HeaderCell("Entrada", 1.6f)
                    HeaderCell("Salida", 1.6f)
                    HeaderCell("Estado", 1.0f)
                    HeaderCell("Activo", 0.8f)
                }

                Divider()

                // Lista de filas
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp, max = 360.dp)
                ) {
                    items(
                        items = carritos,
                        key = { it.idCarrito } // opcional: keys estables
                    ) { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            BodyCell(item.idCarrito.toString(), 0.8f)
                            BodyCell(item.carritoNum.toString(), 0.8f)
                            BodyCell(item.fechaEntrada.format(), 1.6f)
                            BodyCell(item.fechaSalida?.format() ?: "-", 1.6f)
                            BodyCell(item.estadoSalida, 1.0f)
                            BodyCell(if (item.status) "Sí" else "No", 0.8f)
                        }
                        Divider()
                    }
                }

            }
        }
    )
}

// Celdas helper
@Composable
private fun HeaderCell(text: String, weight: Float) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        maxLines = 1
    )
}

@Composable
private fun BodyCell(text: String, weight: Float) {
    Text(
        text = text,
        maxLines = 1
    )
}

// Formateo de fechas
private fun Date.format(): String {
    val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
    return sdf.format(this)
}
