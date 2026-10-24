package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.local.MedicionEntity
import com.example.myapplication.model.EstadoCloro

@Composable
fun MedicionCard(
    medicion: MedicionEntity,
    onVerDetalle: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = medicion.granja,
                style = MaterialTheme.typography.titleMedium
            )
            Text("Punto: ${medicion.punto}")
            Text("Nivel: ${medicion.nivel} mg/L")
            val estado = EstadoCloro.desdeNivel(medicion.nivel)

            Text(
                text = "Estado: ${estado?.name ?: "Sin clasificar"}",
                color = when (estado) {
                    EstadoCloro.NORMAL ->
                        MaterialTheme.colorScheme.primary
                    EstadoCloro.ADVERTENCIA ->
                        MaterialTheme.colorScheme.tertiary
                    EstadoCloro.CRITICO ->
                        MaterialTheme.colorScheme.error
                    null ->
                        MaterialTheme.colorScheme.secondary
                },
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedButton(onClick = onVerDetalle) {
                Text("Ver detalle")
            }
        }
    }
}