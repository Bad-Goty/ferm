package com.example.ferm.presentation.components.datos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ferm.R
import com.example.ferm.presentation.viewmodel.CarritosViewModel

@Composable
fun EstadosConteo(
    modifier: Modifier = Modifier,
    viewModel: CarritosViewModel = hiltViewModel()
) {
    val activos by viewModel.activos.collectAsStateWithLifecycle()
    val status by viewModel.statusHoy.collectAsStateWithLifecycle()

    // Normalizamos para que siempre existan claves
    val mapa = remember (status) {
        val base = mutableMapOf("proceso" to 0, "bien" to 0, "mal" to 0)
        status.forEach { base[it.estadoSalida.lowercase()] = it.cantidad }
        base
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 8.dp)
            .background(Color.White)
            .border(width = 2.dp, color = Color.Black)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(painter = painterResource(R.drawable.reloj), contentDescription = "Proceso", modifier = Modifier.size(30.dp))
                    Text("Activos: $activos", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                VerticalDivider()
                Row(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(painter = painterResource(R.drawable.completo), contentDescription = "Completos", modifier = Modifier.size(35.dp))
                    Text("Completos: ${mapa["bien"]}", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
            HorizontalDivider()
            Box(modifier.fillMaxSize().weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(painter = painterResource(R.drawable.disminucion), contentDescription = "Error", modifier = Modifier.size(35.dp))
                    Text(
                        "Error: ${ (mapa["mal"] ?: 0) + (mapa["proceso"] ?: 0) }",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )

                }
            }
        }
    }
}
