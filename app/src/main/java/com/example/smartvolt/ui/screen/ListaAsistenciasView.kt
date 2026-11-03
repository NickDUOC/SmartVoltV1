package com.example.smartvolt.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvolt.ui.component.ComponenteTarjetaAsistencia
import com.example.smartvolt.viewmodel.FormularioViewModel

@Composable
fun ListaAsistenciasView(viewModel: FormularioViewModel = viewModel()) {
    val asistencias by viewModel.listaAsistencias.collectAsState(initial = emptyList())
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = "Historial de Asistencias",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        // boton mockup sincronizacion con server
        Button(
            onClick = { viewModel.sincronizarConServidor() },
            enabled = !uiState.isSincronizandoServidor && asistencias.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Enviar datos al servidor", color = Color.White)
        }

        uiState.mensajeSincronizacion?.let { msj ->
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = msj,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1565C0)
            )
        }

        if (uiState.isSincronizandoServidor) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (asistencias.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay asistencias registradas aún",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(asistencias) { asistencia ->
                    ComponenteTarjetaAsistencia(asistencia = asistencia)
                }
            }
        }
    }
}