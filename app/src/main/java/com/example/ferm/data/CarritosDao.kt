package com.example.ferm.data
import androidx.room.*
import com.example.ferm.data.entity.CarritoEntity
import com.example.ferm.data.models.CarritoActivoDto
import com.example.ferm.data.models.CarritoFinalizadoDto
import com.example.ferm.data.models.EstadoCountDto
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface CarritosDao {

    // Insertar un carrito
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(carrito: CarritoEntity): Long

    // Obtener todos los carritos ordenados por id_carrito DESC
    @Query("""
        SELECT * 
        FROM Carritos
        WHERE date(fecha_entrada / 1000, 'unixepoch') = date('now') AND status = 0
        ORDER BY id_carrito DESC
    """)
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

    //----------------------SALIDA------------------------------------
    @Query("SELECT COUNT(*) FROM Carritos WHERE status = 1")
    suspend fun countActivos(): Int

    @Query("""
        UPDATE Carritos
        SET fecha_salida = :fechaSalida,
            estado_salida = :estadoSalida,
            status = 0
        WHERE id_carrito = (
            SELECT id_carrito FROM Carritos
            WHERE status = 1
            ORDER BY fecha_entrada ASC
            LIMIT 1
        )
    """)
    suspend fun deactivateOldestActive(
        fechaSalida: Date,
        estadoSalida: String
    ): Int

    @Query("SELECT * FROM Carritos WHERE status = 1 ORDER BY fecha_entrada ASC LIMIT 1")
    suspend fun getOldestActive(): CarritoEntity?
    //----------------------SALIDA------------------------------------


    //----------------------CONTEO STATUS------------------------------------
    @Query("SELECT COUNT(*) FROM Carritos WHERE status = 1")
    fun observeCountActivos(): Flow<Int>

    @Query("""
    SELECT estado_salida AS estadoSalida, COUNT(*) AS cantidad
    FROM Carritos
    WHERE date(fecha_salida/1000, 'unixepoch', 'localtime') = date('now','localtime')
    GROUP BY estado_salida
""")
    fun observeStatusHoy(): Flow<List<EstadoCountDto>>

    //----------------------CONTEO STATUS------------------------------------

    @Query("""
        SELECT 
            carrito_num AS carritoNum,
            strftime('%H:%M:%S', fecha_entrada/1000, 'unixepoch', 'localtime') AS horaEntrada,
            strftime('%H:%M:%S', fecha_salida  /1000, 'unixepoch', 'localtime') AS horaSalida,
            estado_salida AS estadoSalida
        FROM Carritos
        WHERE status = 0
          AND date(fecha_salida/1000, 'unixepoch', 'localtime') = date('now','localtime')
        ORDER BY fecha_salida DESC
        LIMIT 30
    """)
    fun getCarrosFinalizadosHoyTop30(): Flow<List<CarritoFinalizadoDto>>


}