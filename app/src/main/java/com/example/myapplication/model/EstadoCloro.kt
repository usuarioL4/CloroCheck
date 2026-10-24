package com.example.myapplication.model



enum class EstadoCloro {
    NORMAL, ADVERTENCIA, CRITICO;

    companion object {
        fun desdeNivel(nivel: Double): EstadoCloro {
            return when {
                nivel < 2.0 -> CRITICO
                nivel < 3.0 -> ADVERTENCIA
                nivel <= 5.0 -> NORMAL
                nivel <= 6.0 -> ADVERTENCIA
                else -> CRITICO
            }
        }
        fun mensajeDesdeNivel(nivel: Double): String = when {
            nivel < 2.0 -> "Cloro insuficiente: nivel crítico"
            nivel < 3.0 -> "Cloro bajo: requiere revisión"
            nivel <= 5.0 -> "Nivel de cloro normal"
            nivel <= 6.0 -> "Cloro elevado: requiere revisión"
            else -> "Cloro excesivo: nivel crítico"
        }
    }
}
