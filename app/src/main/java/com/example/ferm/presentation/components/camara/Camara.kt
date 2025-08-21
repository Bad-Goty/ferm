package com.example.ferm.presentation.components.camara

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ferm.data.models.CarritoActivoDto

@Composable
fun Camara(
    modifier: Modifier,
    filas: Int,
    columnas: Int,
    activos: List<CarritoActivoDto> // viene de getActivosFormateados()
) {
    // Tick global cada segundo (recarga los cronómetros)
    val now by produceState(initialValue = System.currentTimeMillis(), activos) {
        while (true) {
            value = System.currentTimeMillis()
            kotlinx.coroutines.delay(1_000)
        }
    }

    // Convertimos fecha/hora -> millis (una vez por cambio de 'activos')
    val activosTimer = remember(activos) {
        activos.mapNotNull { dto ->
            parseEntradaMillis(dto.fecha, dto.hora)?.let { ms ->
                CarritoActivoTimerDto(dto.carritoNum, ms)
            }
        }
    }

    // *** AQUÍ EL CAMBIO: usamos la variante por NUMERO en orden inverso (abajo -> arriba) ***
    val celdas = remember(filas, columnas, activosTimer) {
        mapTimerDtoToGridPorNumeroReversa(activosTimer, filas, columnas)
    }

    Box(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            for (col in 0 until columnas) {
                Column {
                    for (row in 0 until filas) {
                        val index = col * filas + row
                        val item = celdas.getOrNull(index)

                        if (item == null) {
                            // Celda vacía
                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .size(150.dp) // 150 si quieres más grande
                                    .background(Color.Black)
                            )
                        } else {
                            val elapsed = (now - item.entradaMillis).coerceAtLeast(0)
                            val elapsedSec = elapsed / 1000
                            val color = colorPorElapsed(elapsedSec)
                            val estado = estadoPorElapsed(elapsedSec)

                            Box(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .size(150.dp)
                                    .background(color),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Carrito: ${item.carritoNum}",
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = formatElapsed(elapsed), // mm:ss u hh:mm:ss
                                        color = Color.Black,
                                        fontSize = 25.sp
                                    )
                                    Text(
                                        text = estado, // "proceso" | "bien" | "mal"
                                        color = Color.Black,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ---- MODELOS AUXILIARES ----
data class CarritoActivoTimerDto(val carritoNum: Int, val entradaMillis: Long)

// ---- HELPERS ----
private fun parseEntradaMillis(fecha: String, hora: String): Long? {
    // "yyyy-MM-dd" + "HH:mm:ss" -> millis (zona local)
    val s = "$fecha $hora"
    return try {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
        sdf.timeZone = java.util.TimeZone.getDefault()
        sdf.parse(s)?.time
    } catch (_: Exception) {
        null
    }
}

/**
 * Reglas:
 * - Se agrupa por "fila lógica" derivada del número: r = (num-1) % filas  -> 1,4,7... misma fila lógica.
 * - Dentro de cada fila lógica, se ordena por "columna lógica" (1,4,7...).
 * - Al pintar, esa fila lógica se coloca **invertida verticalmente**: abajo primero (fila visible = filas-1-r).
 * - Resultado: en una 3x3 el primer carrito visible cae en (fila=2, col=0).
 */
private fun mapTimerDtoToGridPorNumeroReversa(
    activos: List<CarritoActivoTimerDto>,
    filas: Int,
    columnas: Int
): List<CarritoActivoTimerDto?> {
    val porFila = Array(filas) { mutableListOf<CarritoActivoTimerDto>() }
    for (dto in activos) {
        val r = (dto.carritoNum - 1).mod(filas)   // fila lógica por número
        porFila[r].add(dto)
    }

    // Garantiza 1,4,7... en cada fila lógica (orden por columna lógica ascendente)
    for (r in 0 until filas) {
        porFila[r].sortBy { (it.carritoNum - 1) / filas }
    }

    val total = filas * columnas
    val celdas = MutableList<CarritoActivoTimerDto?>(total) { null }
    for (r in 0 until filas) {
        val rowList = porFila[r]
        val maxCols = minOf(columnas, rowList.size)
        for (c in 0 until maxCols) {
            val idx = c * filas + (filas - 1 - r) // pinta esa fila lógica abajo→arriba (visible)
            celdas[idx] = rowList[c]
        }
    }
    return celdas
}

private fun formatElapsed(ms: Long): String {
    val totalSec = ms.coerceAtLeast(0) / 1000
    val h = totalSec / 3600
    val m = (totalSec % 3600) / 60
    val s = totalSec % 60
    return if (h == 0L) "%02d:%02d".format(m, s) else "%02d:%02d:%02d".format(h, m, s)
}

// UMBRALES: Amarillo 0:00–44:59, Verde 45:00–47:59, Rojo 48:00+
private fun colorPorElapsed(sec: Long): Color =
    when {
        sec < 2880 -> Color.Yellow   // 0–2699
        sec < 3120 -> Color.Green    // 2700–2879
        else -> Color.Red            // 2880+
    }

private fun estadoPorElapsed(sec: Long): String =
    when {
        sec < 2880 -> "proceso"
        sec < 3120 -> "bien"
        else -> "mal"
    }
