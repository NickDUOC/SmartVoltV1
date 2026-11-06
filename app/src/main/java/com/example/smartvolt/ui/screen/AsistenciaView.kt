package com.example.smartvolt.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartvolt.viewmodel.FormularioViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import kotlinx.coroutines.tasks.await

@OptIn(MapboxExperimental::class)
@SuppressLint("MissingPermission")
@Composable
fun AsistenciaView(viewModel: FormularioViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // google fused location
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var userLocation by remember { mutableStateOf<Pair<Double, Double>?>(null) }
    var locationMessage by remember { mutableStateOf("Obteniendo posición GPS...") }

    // hacerle fetch a la ubi
    suspend fun fetchCurrentLocation() {
        try {
            val location = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).await()

            if (location != null) {
                userLocation = Pair(location.latitude, location.longitude)
                locationMessage = "Obtenida."
            } else {
                userLocation = Pair(-12.345, -54.321)
            }
        } catch (e: Exception) {
            userLocation = Pair(-12.345, -54.321)
        }
    }

    // cosa pa pedir permisos
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                locationMessage = "Permisos obtenidos."
            } else {
                locationMessage = "Permiso denegados."
            }
        }
    )

    // asegurar q se solicitan permisos al launch
    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    LaunchedEffect(locationMessage) {
        fetchCurrentLocation()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Control de Asistencia",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // card personal
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "ID Operario: ${uiState.operarioId}", fontWeight = FontWeight.Bold)
                Text(text = "Faena: ${uiState.faena}", fontSize = 14.sp)
                Text(
                    text = "Estado: ${uiState.estadoActual}",
                    fontWeight = FontWeight.Bold,
                    color = if (uiState.estadoActual == "EN FAENA") Color(0xFF2E7D32) else Color(0xFFC62828)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Visor del Mapa de la Faena / Ubicación Actual
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            userLocation?.let { (lat, lon) ->
                MapboxMap(
                    Modifier.fillMaxSize(),
                    mapViewportState = rememberMapViewportState {
                        setCameraOptions {
                            zoom(15.0)
                            center(Point.fromLngLat(lon, lat))
                        }
                    }
                ) {
                    PointAnnotation(point = Point.fromLngLat(lon, lat))
                }
            } ?: run {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = locationMessage, fontSize = 14.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isVerificandoGps) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Verificando entorno GPS", fontSize = 13.sp)
        } else {
            // coords actuales
            val currentLat = userLocation?.first ?: -23.6509
            val currentLon = userLocation?.second ?: -70.3975

            Row(modifier = Modifier.fillMaxWidth()) {
                // entrada
                Button(
                    onClick = { viewModel.registrarMarca("ENTRADA", currentLat, currentLon) },
                    enabled = uiState.proximoTipoMarcaPermitido == "ENTRADA",
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                ) {
                    Text("Marcar Entrada")
                }

                // salida
                Button(
                    onClick = { viewModel.registrarMarca("SALIDA", currentLat, currentLon) },
                    enabled = uiState.proximoTipoMarcaPermitido == "SALIDA",
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                ) {
                    Text("Marcar Salida")
                }
            }
        }
    }
}
