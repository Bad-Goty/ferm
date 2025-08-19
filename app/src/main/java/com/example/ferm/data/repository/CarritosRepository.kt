package com.example.ferm.data.repository

import com.example.ferm.data.CarritosDao
import com.example.ferm.data.entity.CarritoEntity
import com.example.ferm.data.models.CarritoActivoDto
import com.example.ferm.data.models.CarritoFinalizadoDto
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class CarritosRepository @Inject constructor(
    private val dao: CarritosDao
) {
    fun getAllCarritosDesc(): Flow<List<CarritoEntity>> =
        dao.getAllCarritosDesc()

    fun observeMaxNumHoy(): Flow<Int> =
        dao.observeMaxNumHoy()

    fun getActivosFormateados(): Flow<List<CarritoActivoDto>> =
        dao.getActivosFormateados()

    suspend fun agregarCarritoAuto(): Long {
        val maxHoy = dao.currentMaxNumHoy() // <-- aquí usamos la suspend
        val siguienteNum = maxHoy + 1
        val entity = CarritoEntity(
            carritoNum = siguienteNum,
            fechaEntrada = Date(),
            fechaSalida = null,
            estadoSalida = "proceso",
            status = true
        )
        return dao.insert(entity)
    }

    //----------------------SALIDA------------------------------------
// Umbrales en segundos
    private companion object {
        const val YELLOW_MAX = 2700L // 0:00–44:59
        const val GREEN_MAX  = 2880L // 45:00–47:59 (>=2880 rojo)
    }

    /**
     * Quita el más antiguo activo calculando el estado según tiempo transcurrido.
     * Devuelve true si actualizó 1 fila; false si no había activos.
     */
    suspend fun quitarPrimeroAutoEstado(): Boolean {
        val oldest = dao.getOldestActive() ?: return false
        val elapsedSec = (System.currentTimeMillis() - oldest.fechaEntrada.time) / 1000
        val estado = when {
            elapsedSec < YELLOW_MAX -> "proceso"
            elapsedSec < GREEN_MAX  -> "bien"
            else                    -> "mal"
        }
        val rows = dao.deactivateOldestActive(Date(), estado)
        return rows > 0
    }

    /**
     * Igual que el anterior pero evitando dejar en 0 activos (mínimo 1).
     */
    suspend fun quitarMasAntiguoHasta(minActivos: Int = 1): Boolean {
        if (dao.countActivos() <= minActivos) return false
        return quitarPrimeroAutoEstado()
    }
//----------------------SALIDA------------------------------------


    //----------------------CONTEO STATUS------------------------------------
    fun observeCountActivos() = dao.observeCountActivos()
    fun observeStatusHoy() = dao.observeStatusHoy()
    //----------------------CONTEO STATUS------------------------------------



    fun getCarrosFinalizadosHoyTop30(): Flow<List<CarritoFinalizadoDto>> =
        dao.getCarrosFinalizadosHoyTop30()
}
