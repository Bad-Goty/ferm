package com.example.ferm.data.entity

import androidx.room.*
import java.util.Date

@Entity(tableName = "Carritos")
data class CarritoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_carrito") val idCarrito: Int = 0,
    @ColumnInfo(name = "carrito_num") val carritoNum: Int,
    @ColumnInfo(name = "fecha_entrada") val fechaEntrada: Date,
    @ColumnInfo(name = "fecha_salida") val fechaSalida: Date? = null,
    @ColumnInfo(name = "estado_salida") val estadoSalida: String = "proceso",
    @ColumnInfo(name = "status") val status: Boolean = true
)