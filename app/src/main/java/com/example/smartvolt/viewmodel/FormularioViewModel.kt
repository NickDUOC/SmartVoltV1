package com.example.smartvolt.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartvolt.model.AppDatabase
import com.example.smartvolt.model.Asistencia
import com.example.smartvolt.model.FormularioModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.delay

class FormularioViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).asistenciaDAO()

    // lista asistencias
    val listaAsistencias: StateFlow<List<Asistencia>> = dao.listarAsistencias().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _uiState = MutableStateFlow(FormularioModel())
    val uiState: StateFlow<FormularioModel> = _uiState.asStateFlow()

    init {
        // revisa si da check in o out
        viewModelScope.launch {
            dao.obtenerUltimaAsistencia(_uiState.value.operarioId).collect { ultimaAsistencia ->
                if (ultimaAsistencia != null && ultimaAsistencia.tipoMarca == "ENTRADA") {
                    _uiState.update {
                        it.copy(
                            estadoActual = "EN FAENA",
                            proximoTipoMarcaPermitido = "SALIDA"
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            estadoActual = "FUERA DE FAENA",
                            proximoTipoMarcaPermitido = "ENTRADA"
                        )
                    }
                }
            }
        }
    }

    // mockup de verificación GPS de developer
    fun registrarMarca(tipoMarca: String) {
        if (tipoMarca != _uiState.value.proximoTipoMarcaPermitido) return

        viewModelScope.launch {
            _uiState.update { it.copy(isVerificandoGps = true) }

            delay(3000)

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val fechaHoraActual = sdf.format(Date())

            val nuevaAsistencia = Asistencia(
                operarioId = _uiState.value.operarioId,
                faena = _uiState.value.faena,
                tipoMarca = tipoMarca,
                fechaHora = fechaHoraActual,
                latitud = -12.3456,
                longitud = -65.4321,
                sincronizado = false
            )

            dao.agregarAsistencia(nuevaAsistencia)
            _uiState.update { it.copy(isVerificandoGps = false) }
        }
    }

    // mockup sincro con servidor
    fun sincronizarConServidor() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSincronizandoServidor = true,
                    mensajeSincronizacion = "Conectando con servidores de SmartVolt..."
                )
            }

            delay(2500)

            dao.marcarComoSincronizados()

            _uiState.update {
                it.copy(
                    isSincronizandoServidor = false,
                    mensajeSincronizacion = "Registros guardados en el servidor."
                )
            }

            delay(3000)
            _uiState.update { it.copy(mensajeSincronizacion = null) }
        }
    }
}