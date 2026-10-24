package com.example.myapplication.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "alertas")
data class AlertaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val medicionId: Long,

    @ColumnInfo(defaultValue = "''")
    val granja: String = "",

    @ColumnInfo(defaultValue = "''")
    val punto: String = "",

    val nivel: Double,
    val estado: String,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val abierta: Boolean = true,
    val accionCorrectiva: String? = null,
    val fechaCierre: Long? = null
)
