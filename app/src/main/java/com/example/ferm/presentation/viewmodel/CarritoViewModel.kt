package com.example.ferm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ferm.data.entity.CarritoEntity
import com.example.ferm.data.models.CarritoActivoDto
import com.example.ferm.data.repository.CarritosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class CarritosViewModel @Inject constructor(
    repo: CarritosRepository
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

  /*  fun agregarCarrito() = viewModelScope.launch {
        repo
    }*/
}