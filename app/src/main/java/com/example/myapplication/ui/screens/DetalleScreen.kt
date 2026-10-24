package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.local.MedicionEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    medicion: MedicionEntity,
    onGuardarAccion: (String) -> Unit,
    onVolver: () -> Unit
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

            Text(
                if (medicion.nivel < 0.8) {
                    "Estado: Revisar"
                } else {
                    "Estado: Normal"
                },
                style = MaterialTheme.typography.titleMedium
            )

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