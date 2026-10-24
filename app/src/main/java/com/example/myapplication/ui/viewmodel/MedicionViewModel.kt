package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.MedicionEntity
import com.example.myapplication.data.repository.MedicionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MedicionViewModel(
    private val repository: MedicionRepository
) : ViewModel() {

    val mediciones: StateFlow<List<MedicionEntity>> =
        repository.observarTodas().stateIn(
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
            repository.insertar(
                MedicionEntity(
                    granja = granja.trim(),
                    punto = punto.trim(),
                    nivel = nivel
                )
            )
        }
    }

    fun guardarAccion(id: Long, accion: String) {
        viewModelScope.launch {
            repository.actualizarAccion(id, accion.trim())
        }
    }
}

class MedicionViewModelFactory(
    private val repository: MedicionRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MedicionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MedicionViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}