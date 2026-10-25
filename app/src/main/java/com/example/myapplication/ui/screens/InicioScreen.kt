package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.local.MedicionEntity
import com.example.myapplication.model.EstadoCloro
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    mediciones: List<MedicionEntity>,
    totalAlertasAbiertas: Int,
    onVerMediciones: () -> Unit,
    onRegistrar: () -> Unit
) {
    val normales = mediciones.count {
        EstadoCloro.desdeNivel(it.nivel) == EstadoCloro.NORMAL
    }

    val advertencias = mediciones.count {
        EstadoCloro.desdeNivel(it.nivel) == EstadoCloro.ADVERTENCIA
    }

    val criticas = mediciones.count {
        EstadoCloro.desdeNivel(it.nivel) == EstadoCloro.CRITICO
    }

    val promedio = if (mediciones.isNotEmpty()) {
        mediciones.map { it.nivel }.average()
    } else {
        0.0
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Control de cloro") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Panel de control",
                style = MaterialTheme.typography.headlineSmall
            )

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Total de mediciones")
                    Text(
                        "${mediciones.size}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Alertas abiertas")
                    Text(
                        "$totalAlertasAbiertas",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Promedio de cloro")
                    Text(
                        if (mediciones.isEmpty()) "Sin datos"
                        else String.format(
                            Locale.getDefault(),
                            "%.2f",
                            promedio
                        ),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
            Text(
                text = "Valores: ${mediciones.map { it.nivel }}"
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Estado de las mediciones",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text("Normales: $normales")
                    Text("Advertencias: $advertencias")
                    Text("Críticas: $criticas")
                }
            }

            Spacer(Modifier.height(4.dp))

            Button(
                onClick = onVerMediciones,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver historial")
            }

            OutlinedButton(
                onClick = onRegistrar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar medición")
            }

        }
    }
}
