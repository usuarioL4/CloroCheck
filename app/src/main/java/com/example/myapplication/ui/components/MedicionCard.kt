package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.local.MedicionEntity

@Composable
fun MedicionCard(
    medicion: MedicionEntity,
    onVerDetalle: () -> Unit
) {
    val revisar = medicion.nivel < 0.8

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

            Text(
                text = if (revisar) "Revisar" else "Normal",
                color = if (revisar) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )

            OutlinedButton(onClick = onVerDetalle) {
                Text("Ver detalle")
            }
        }
    }
}