package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.AlertaEntity
import com.example.myapplication.data.local.MedicionEntity
import com.example.myapplication.data.repository.AlertaRepository
import com.example.myapplication.data.repository.MedicionRepository
import com.example.myapplication.model.EstadoCloro
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MedicionViewModel(
    private val repository: MedicionRepository,
    private val alertaRepository: AlertaRepository
) : ViewModel() {

    val mediciones: StateFlow<List<MedicionEntity>> =
        repository.observarTodas().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val alertasAbiertas: StateFlow<List<AlertaEntity>> =
        alertaRepository.observarAbiertas().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun agregarMedicion(
        granja: String,
        punto: String,
        nivel: Double
    ) {
        viewModelScope.launch {
            val medicion = MedicionEntity(
                granja = granja.trim(),
                punto = punto.trim(),
                nivel = nivel
            )

            val idMedicion = repository.insertar(medicion)
            val estado = EstadoCloro.desdeNivel(nivel)

            if (estado != EstadoCloro.NORMAL) {
                alertaRepository.insertar(
                    AlertaEntity(
                        medicionId = idMedicion,
                        granja = medicion.granja,
                        punto = medicion.punto,
                        nivel = nivel,
                        estado = estado.name
                    ))
            }
        }
    }

    fun guardarAccion(id: Long, accion: String) {
        viewModelScope.launch {
            repository.actualizarAccion(id, accion.trim())
        }
    }

    fun guardarAccionAlerta(id: Long, accion: String) {
        viewModelScope.launch {
            alertaRepository.actualizarAccion(id, accion.trim())
        }
    }
    fun cerrarAlerta(id: Long) {
        viewModelScope.launch {
            alertaRepository.cerrarAlerta(
                id,
                System.currentTimeMillis()
            )
        }
    }
    fun eliminarMedicion(id: Long) {
        viewModelScope.launch {
            alertaRepository.eliminarPorMedicionId(id)
            repository.eliminarPorId(id)
        }
    }
}

class MedicionViewModelFactory(
    private val repository: MedicionRepository,
    private val alertaRepository: AlertaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(MedicionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MedicionViewModel(
                repository,
                alertaRepository
            ) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}