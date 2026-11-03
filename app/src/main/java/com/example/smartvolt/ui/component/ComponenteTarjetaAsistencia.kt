package com.example.smartvolt.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartvolt.model.Asistencia

@Composable
fun ComponenteTarjetaAsistencia(asistencia: Asistencia) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "GPS",
                tint = if (asistencia.tipoMarca == "ENTRADA") Color(0xFF2E7D32) else Color(0xFFC62828),
                modifier = Modifier.padding(end = 12.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Marca: ${asistencia.tipoMarca}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (asistencia.tipoMarca == "ENTRADA") Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (asistencia.sincronizado) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Sincronizado",
                                tint = Color(0xFF1976D2),
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Text(text = "Servidor", fontSize = 11.sp, color = Color(0xFF1976D2))
                        }
                    } else {
                        Text(text = "Local (Offline)", fontSize = 11.sp, color = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Operario: ${asistencia.operarioId} | Faena: ${asistencia.faena}", fontSize = 13.sp)
                Text(text = "Fecha y Hora: ${asistencia.fechaHora}", fontSize = 12.sp, color = Color.DarkGray)
                Text(text = "Ubicación: Lat ${asistencia.latitud}, Lon ${asistencia.longitud}", fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}