package com.example.ferm.data.repository

import com.example.ferm.data.CarritosDao
import com.example.ferm.data.entity.CarritoEntity
import com.example.ferm.data.models.CarritoActivoDto
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

}
