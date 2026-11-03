package com.example.smartvolt.model

data class FormularioModel(
    //datos placeholder
    val operarioId: String = "OP-8492",
    val faena: String = "Smart-Volt Norte",
    val estadoActual: String = "FUERA DE FAENA",
    val proximoTipoMarcaPermitido: String = "ENTRADA",
    val isVerificandoGps: Boolean = false,
    val isSincronizandoServidor: Boolean = false,
    val mensajeSincronizacion: String? = null
)

