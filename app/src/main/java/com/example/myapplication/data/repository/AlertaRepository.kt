package com.example.myapplication.data.repository

import com.example.myapplication.data.local.AlertaDao
import com.example.myapplication.data.local.AlertaEntity
import kotlinx.coroutines.flow.Flow

class AlertaRepository(
    private val dao: AlertaDao
) {
    suspend fun insertar(alerta: AlertaEntity): Long =
        dao.insertar(alerta)

    suspend fun actualizarAccion(id: Long, accion: String) {
        dao.actualizarAccion(id, accion)
    }

    fun observarTodas(): Flow<List<AlertaEntity>> =
        dao.observarTodas()

    fun observarAbiertas(): Flow<List<AlertaEntity>> =
        dao.observarAbiertas()

    suspend fun cerrarAlerta(id: Long, fecha: Long) {
        dao.cerrarAlerta(id, fecha)
    }
    suspend fun eliminarPorMedicionId(medicionId: Long) {
        dao.eliminarPorMedicionId(medicionId)
    }
}