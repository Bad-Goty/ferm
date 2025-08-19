package com.example.ferm.data.models

data class CarritoFinalizadoDto(
    val carritoNum: Int,
    val horaEntrada: String, // "HH:mm:ss"
    val horaSalida: String,  // "HH:mm:ss"
    val estadoSalida: String
)
