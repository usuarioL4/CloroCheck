package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.local.MedicionEntity
import com.example.myapplication.model.EstadoCloro
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    medicion: MedicionEntity,
    onGuardarAccion: (String) -> Unit,
    onVolver: () -> Unit,
    onEliminar: () -> Unit
) {
    var accion by remember(medicion.id) {
        mutableStateOf(medicion.accionCorrectiva.orEmpty())
    }

    val fecha = remember(medicion.fechaRegistro) {
        SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            Locale.getDefault()
        ).format(Date(medicion.fechaRegistro))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de medición") },
                navigationIcon = {
                    TextButton(onClick = onVolver) {
                        Text("Volver")
                    }
                },
                actions = {
                    IconButton(onClick = onEliminar) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar medición",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Granja: ${medicion.granja}")
            Text("Punto: ${medicion.punto}")
            Text("Nivel: ${medicion.nivel} mg/L")
            Text("Fecha: $fecha")
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
            if(!medicion.accionCorrectiva.isNullOrBlank()){
                Text("Acción guardada: ${medicion.accionCorrectiva}")
            }
            OutlinedTextField(
                value = accion,
                onValueChange = { accion = it },
                label = { Text("Acción correctiva / observaciones") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Button(
                onClick = { onGuardarAccion(accion) },
                modifier = Modifier.fillMaxWidth(),
                enabled = accion.isNotBlank()
            ) {
                Text("Guardar observación")
            }

        }
    }
}