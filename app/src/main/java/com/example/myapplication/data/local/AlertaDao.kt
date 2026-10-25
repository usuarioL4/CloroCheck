package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertaDao {

    @Insert
    suspend fun insertar(alerta: AlertaEntity): Long

    @Query("""
        SELECT * FROM alertas
        ORDER BY fechaCreacion DESC
    """)
    fun observarTodas(): Flow<List<AlertaEntity>>

    @Query("""
        SELECT * FROM alertas
        WHERE abierta = 1
        ORDER BY fechaCreacion DESC
    """)
    fun observarAbiertas(): Flow<List<AlertaEntity>>

    @Query("UPDATE alertas SET accionCorrectiva = :accion WHERE id = :id")
    suspend fun actualizarAccion(id: Long, accion: String)
    @Query("""
    UPDATE alertas
    SET abierta = 0, fechaCierre = :fecha
    WHERE id = :id
""")
    suspend fun cerrarAlerta(id: Long, fecha: Long)
    @Query("DELETE FROM alertas WHERE medicionId = :medicionId")
    suspend fun eliminarPorMedicionId(medicionId: Long)
}