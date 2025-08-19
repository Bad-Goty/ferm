package com.example.ferm.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ferm.presentation.components.CarritosTableDialog
import com.example.ferm.presentation.components.camara.Camara
import com.example.ferm.presentation.components.datos.Datos
import com.example.ferm.presentation.viewmodel.CarritosViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: CarritosViewModel = hiltViewModel()
) {
    val filas = 4
    val columnas = 5
    val capacidad = filas * columnas
    val activosUi by viewModel.carritosActivosUi.collectAsStateWithLifecycle() // <- activos formateados
    val puedeAgregar = activosUi.size < capacidad
    val carritos by viewModel.carritos.collectAsStateWithLifecycle()


    var showTabla by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxSize().background(Color(0xFFCFCDD3))
    ) {
        Datos(
            modifier = Modifier.weight(1f),
            onAgregar = { if (puedeAgregar) viewModel.agregarCarrito() },
            onQuitarPrimero = { viewModel.quitarPrimero() } // o quitarPrimeroConMinimo()
        )
        Camara(
            modifier = Modifier.weight(2.5f),
            filas = filas,
            columnas = columnas,
            // pasamos solo los números para la grilla
            activos = activosUi   // ← usamos el DTO formateado
        )
    }

    /*Button(onClick = { showTabla = true }) {
        Text("Ver tabla")
    }*/

    if (showTabla) {
        CarritosTableDialog(
            carritos = carritos,
            onDismiss = { showTabla = false }
        )
    }
}
