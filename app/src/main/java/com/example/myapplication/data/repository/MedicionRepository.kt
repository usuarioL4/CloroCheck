package com.example.myapplication.data.repository


import com.example.myapplication.data.local.MedicionDao
import com.example.myapplication.data.local.MedicionEntity
import kotlinx.coroutines.flow.Flow

class MedicionRepository(
    private val dao: MedicionDao
) {
    fun observarTodas(): Flow<List<MedicionEntity>> =
        dao.observarTodas()

    suspend fun buscarPorId(id: Long): MedicionEntity? =
        dao.buscarPorId(id)

    suspend fun insertar(medicion: MedicionEntity): Long =
        dao.insertar(medicion)

    suspend fun actualizarAccion(id: Long, accion: String) =
        dao.actualizarAccion(id, accion)
    suspend fun eliminarPorId(id: Long) {
        dao.eliminarPorId(id)
    }
}