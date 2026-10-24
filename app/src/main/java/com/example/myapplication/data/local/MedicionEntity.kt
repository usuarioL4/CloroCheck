package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mediciones")
data class MedicionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val granja: String,
    val punto: String,
    val nivel: Double,

    val fechaRegistro: Long = System.currentTimeMillis(),

    val accionCorrectiva: String? = null
)