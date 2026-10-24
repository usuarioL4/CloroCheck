package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.local.MedicionEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    mediciones: List<MedicionEntity>,
    onVerMediciones: () -> Unit,
    onRegistrar: () -> Unit
) {
    val alertas = mediciones.count { it.nivel < 3.0 || it.nivel > 5.0 }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Control de cloro") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                    Text("Mediciones por revisar")
                    Text(
                        "$alertas",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

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