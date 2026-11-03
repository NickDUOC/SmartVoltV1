package com.example.smartvolt.navigation

sealed class Destinos(val ruta: String) {
    object Splash : Destinos("splash")
    object Login : Destinos("login")
    object Home : Destinos("home")
}
