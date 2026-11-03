package com.example.smartvolt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartvolt.navigation.Destinos
import com.example.smartvolt.ui.theme.SmartVoltTheme
import com.example.smartvolt.ui.screen.LoginView
import com.example.smartvolt.ui.screen.MainContainerView
import com.example.smartvolt.ui.screen.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartVoltTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Destinos.Splash.ruta
                    ) {
                        composable(route = Destinos.Splash.ruta) {
                            SplashScreen(navController = navController)
                        }
                        composable(route = Destinos.Login.ruta) {
                            LoginView(navController = navController)
                        }
                        composable(route = Destinos.Home.ruta) {
                            MainContainerView()
                        }
                    }
                }
            }
        }
    }
}
