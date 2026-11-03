package com.example.smartvolt.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AsistenciaDAO {
    @Insert
    suspend fun agregarAsistencia(asistencia: Asistencia)

    @Query("SELECT * FROM asistencias ORDER BY id DESC")
    fun listarAsistencias(): Flow<List<Asistencia>>

    // revisa el estado actual
    @Query("SELECT * FROM asistencias WHERE operarioId = :operarioId ORDER BY id DESC LIMIT 1")
    fun obtenerUltimaAsistencia(operarioId: String): Flow<Asistencia?>

    // mockup conexion server
    @Query("UPDATE asistencias SET sincronizado = 1 WHERE sincronizado = 0")
    suspend fun marcarComoSincronizados()
}