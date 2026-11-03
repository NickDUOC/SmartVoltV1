package com.example.smartvolt.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asistencias")
data class Asistencia(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val operarioId: String,  // id
    val faena: String,       // faena
    val tipoMarca: String,   // si es entrada o salida
    val fechaHora: String,
    val latitud: Double,     // mockup del gps
    val longitud: Double,    // mockup del gps
    val sincronizado: Boolean = false // si está sincronizado o no
)