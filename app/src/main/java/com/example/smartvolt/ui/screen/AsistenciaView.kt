package com.example.smartvolt.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.smartvolt.viewmodel.FormularioViewModel

@Composable
fun AsistenciaView(viewModel: FormularioViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Control de Asistencia",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // tarjeta con info de la sesión actual (mockup)
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "ID Operario: ${uiState.operarioId}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Faena: ${uiState.faena}", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Estado Actual: ", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = uiState.estadoActual,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (uiState.estadoActual == "EN FAENA") Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.isVerificandoGps) {
            //mockup de verificando el gps y el gps falso
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Verificando entorno GPS...",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            // para el marcaje, depende del estado guardado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { viewModel.registrarMarca("ENTRADA") },
                    enabled = uiState.proximoTipoMarcaPermitido == "ENTRADA",
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(text = "Marcar Entrada", color = Color.White)
                }
                Button(
                    onClick = { viewModel.registrarMarca("SALIDA") },
                    enabled = uiState.proximoTipoMarcaPermitido == "SALIDA",
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(text = "Marcar Salida", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (uiState.proximoTipoMarcaPermitido == "ENTRADA")
                    "Siguiente acción: Entrada"
                else "Siguiente acción: Salida",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}