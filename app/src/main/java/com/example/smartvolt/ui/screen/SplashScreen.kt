package com.example.smartvolt.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartvolt.navigation.Destinos
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    // temporizador para animación
    LaunchedEffect(key1 = true) {
        delay(3000) // 3 segundos de espera
        navController.navigate(route = Destinos.Login.ruta) {
            popUpTo(route = Destinos.Splash.ruta) { inclusive = true }
        }
    }

    // pantalla bienvenida
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "SmartVolt",
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}