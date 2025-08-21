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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.ferm.data.entity.CarritoEntity
import java.util.Date

@Composable
fun CarritosTableDialog(
    carritos: List<CarritoEntity>,
    onDismiss: () -> Unit,
    navController: NavController
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { navController.navigate("statistics")}) { Text("Ver mas") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cerrar") }
        },
        title = { Text("Carritos del día", Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {

                // Encabezados
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //HeaderCell("ID", 0.7f)
                    HeaderCell("Carrito", 0.7f)
                    //HeaderCell("Entrada", 1.2f)
                    //HeaderCell("Salida", 1.2f)
                    HeaderCell("Duración", 1.2f)
                    HeaderCell("Estado", 1.0f)
                    //HeaderCell("Activo", 0.8f)
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
                        key = { it.idCarrito }
                    ) { item ->
                        val entradaTxt = item.fechaEntrada.formatTimeHM()
                        val salidaTxt = item.fechaSalida?.formatTimeHM() ?: "-"
                        val duracionTxt = formatDuration(item.fechaEntrada, item.fechaSalida)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            //BodyCell(item.idCarrito.toString(), 0.7f)
                            BodyCell(item.carritoNum.toString(), 0.7f)
                            //BodyCell(entradaTxt, 1.2f)
                            //BodyCell(salidaTxt, 1.2f)
                            BodyCell(duracionTxt, 1.2f)
                            BodyCell(item.estadoSalida, 1.0f)
                            //BodyCell(if (item.status) "Sí" else "No", 0.8f)
                        }
                        Divider()
                    }
                }
            }
        }
    )
}

// ---------- Helpers de celdas (ahora sí usan weight) ----------
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

// ---------- Formateos y duración ----------
private fun Date.formatTimeHM(): String {
    val sdf = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
    return sdf.format(this)
}

/**
 * Devuelve la duración entre start y end en formato HH:mm:ss.
 * Si end == null, usa el tiempo actual (carrito en proceso).
 * Nunca devuelve duración negativa.
 */
private fun formatDuration(start: Date, end: Date?): String {
    val endMs = end?.time ?: System.currentTimeMillis()
    val diff = (endMs - start.time).coerceAtLeast(0L)

    val hours = java.util.concurrent.TimeUnit.MILLISECONDS.toHours(diff)
    val minutes = java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(diff) % 60
    val seconds = java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(diff) % 60

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
