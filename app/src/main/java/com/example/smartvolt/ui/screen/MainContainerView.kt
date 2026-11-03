package com.example.smartvolt.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainContainerView() {
    val subNavController = rememberNavController()
    val navBackStackEntry by subNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == "marcar",
                    onClick = {
                        subNavController.navigate("marcar") {
                            popUpTo(subNavController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = { Text("Marcar") },
                    icon = { Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Marcar") }
                )
                NavigationBarItem(
                    selected = currentRoute == "historial",
                    onClick = {
                        subNavController.navigate("historial") {
                            popUpTo(subNavController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = { Text("Historial") },
                    icon = { Icon(imageVector = Icons.Default.List, contentDescription = "Historial") }
                )
                NavigationBarItem(
                    selected = currentRoute == "perfil",
                    onClick = {
                        subNavController.navigate("perfil") {
                            popUpTo(subNavController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = { Text("Perfil") },
                    icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Perfil") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = subNavController,
            startDestination = "marcar",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("marcar") {
                AsistenciaView()
            }
            composable("historial") {
                ListaAsistenciasView()
            }
            composable("perfil") {
                PerfilView()
            }
        }
    }
}
