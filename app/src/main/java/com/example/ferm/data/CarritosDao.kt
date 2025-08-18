package com.example.ferm.data
import androidx.room.*
import com.example.ferm.data.entity.CarritoEntity
import com.example.ferm.data.models.CarritoActivoDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritosDao {

    // Insertar un carrito
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(carrito: CarritoEntity): Long

    // Obtener todos los carritos ordenados por id_carrito DESC
    @Query("SELECT * FROM Carritos ORDER BY id_carrito DESC")
    fun getAllCarritosDesc(): Flow<List<CarritoEntity>>

    // 2) Máximo carrito_num de HOY (zona local)
    // Nota: guardando Date como millis => dividir entre 1000 para unixepoch(seg)
    @Query("""
        SELECT COALESCE(MAX(carrito_num), 0) FROM Carritos
        WHERE date(fecha_entrada/1000, 'unixepoch', 'localtime') = date('now','localtime')
    """)
    fun observeMaxNumHoy(): Flow<Int>


    // Máximo carrito_num de HOY (versión "una sola lectura", no Flow)
    @Query("""
        SELECT COALESCE(MAX(carrito_num), 0) FROM Carritos
        WHERE date(fecha_entrada/1000, 'unixepoch', 'localtime') = date('now','localtime')
    """)
    suspend fun currentMaxNumHoy(): Int

    // (Opcional) Si quieres los activos ya con hora/fecha formateada como en tu API:
    @Query("""
        SELECT 
            carrito_num AS carritoNum,
            strftime('%H:%M:%S', fecha_entrada/1000, 'unixepoch', 'localtime') AS hora,
            strftime('%Y-%m-%d', fecha_entrada/1000, 'unixepoch', 'localtime') AS fecha
        FROM Carritos
        WHERE status = 1
        ORDER BY fecha_entrada ASC
    """)
    fun getActivosFormateados(): Flow<List<CarritoActivoDto>>

}