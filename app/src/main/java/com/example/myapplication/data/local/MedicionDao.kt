package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicionDao {

    @Query("SELECT * FROM mediciones ORDER BY fechaRegistro DESC")
    fun observarTodas(): Flow<List<MedicionEntity>>

    @Query("SELECT * FROM mediciones WHERE id = :id")
    suspend fun buscarPorId(id: Long): MedicionEntity?

    @Insert
    suspend fun insertar(medicion: MedicionEntity): Long

    @Query("UPDATE mediciones SET accionCorrectiva = :accion WHERE id = :id")
    suspend fun actualizarAccion(
        id: Long,
        accion: String
    )
    @Query("DELETE FROM mediciones WHERE id = :id")
    suspend fun eliminarPorId(id: Long)
}