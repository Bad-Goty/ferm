package com.example.ferm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ferm.data.entity.CarritoEntity
import com.example.ferm.data.models.CarritoActivoDto
import com.example.ferm.data.models.CarritoFinalizadoDto
import com.example.ferm.data.models.EstadoCountDto
import com.example.ferm.data.repository.CarritosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class CarritosViewModel @Inject constructor(
    private val repo: CarritosRepository
) : ViewModel() {

    // Para tu modal "Ver tabla"
    val carritos: StateFlow<List<CarritoEntity>> =
        repo.getAllCarritosDesc()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    // Para la cámara con hora/fecha ya formateadas
    val carritosActivosUi: StateFlow<List<CarritoActivoDto>> =
        repo.getActivosFormateados()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    // Máximo carrito_num de HOY (Flow<Int>)
    val carritoMaximoHoy: StateFlow<Int> =
        repo.observeMaxNumHoy()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 0
            )

    fun agregarCarrito() = viewModelScope.launch {
        repo.agregarCarritoAuto()
    }




    fun quitarPrimero() = viewModelScope.launch {
        repo.quitarPrimeroAutoEstado()
    }
    // Si quieres asegurar mínimo 1 activo:
    fun quitarPrimeroConMinimo() = viewModelScope.launch {
        repo.quitarMasAntiguoHasta(minActivos = 1)
    }






    //----------------------CONTEO STATUS------------------------------------
    val activos: StateFlow<Int> =
        repo.observeCountActivos().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val statusHoy: StateFlow<List<EstadoCountDto>> =
        repo.observeStatusHoy().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())





    val carrosFinalizadosHoyTop30: StateFlow<List<CarritoFinalizadoDto>> =
        repo.getCarrosFinalizadosHoyTop30()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}