package com.example.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        MedicionEntity::class,
        AlertaEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class MedicionDatabase : RoomDatabase() {

    abstract fun medicionDao(): MedicionDao
    abstract fun alertaDao(): AlertaDao

    companion object {
        @Volatile
        private var INSTANCE: MedicionDatabase? = null
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS alertas (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        medicionId INTEGER NOT NULL,
                        nivel REAL NOT NULL,
                        estado TEXT NOT NULL,
                        fechaCreacion INTEGER NOT NULL,
                        abierta INTEGER NOT NULL,
                        accionCorrectiva TEXT,
                        fechaCierre INTEGER
                    )
                """.trimIndent()
                )
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE alertas ADD COLUMN granja TEXT NOT NULL DEFAULT ''"
                )
                db.execSQL(
                    "ALTER TABLE alertas ADD COLUMN punto TEXT NOT NULL DEFAULT ''"
                )
            }
        }
        fun obtenerInstancia(context: Context): MedicionDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MedicionDatabase::class.java,
                    "mediciones_db"
                )
                    .addMigrations(
                        MIGRATION_1_2,
                        MIGRATION_2_3
                    )
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}