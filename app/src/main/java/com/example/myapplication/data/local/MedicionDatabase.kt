package com.example.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MedicionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MedicionDatabase : RoomDatabase() {

    abstract fun medicionDao(): MedicionDao

    companion object {
        @Volatile
        private var INSTANCE: MedicionDatabase? = null

        fun obtenerInstancia(context: Context): MedicionDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MedicionDatabase::class.java,
                    "mediciones_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}