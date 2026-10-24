package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onGuardar: (String, String, Double) -> Unit,
    onVolver: () -> Unit
) {
    var granja by remember { mutableStateOf("") }
    var punto by remember { mutableStateOf("") }
    var nivelTexto by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar medición") },
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
            OutlinedTextField(
                value = granja,
                onValueChange = { granja = it },
                label = { Text("Nombre de la granja") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = punto,
                onValueChange = { punto = it },
                label = { Text("Punto de medición") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = nivelTexto,
                onValueChange = { nivelTexto = it },
                label = { Text("Nivel de cloro (mg/L)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (error != null) {
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    val nivel = nivelTexto.trim()
                        .replace(',', '.')
                        .toDoubleOrNull()

                    error = when {
                        granja.isBlank() ->
                            "Ingresa el nombre de la granja."

                        punto.isBlank() ->
                            "Ingresa el punto de medición."

                        nivel == null ->
                            "Ingresa un nivel numérico válido."

                        !nivel.isFinite() || nivel < 0.0 ->
                            "El nivel debe ser un número finito no negativo."

                        else -> null
                    }

                    if (error == null && nivel != null) {
                        onGuardar(
                            granja.trim(),
                            punto.trim(),
                            nivel
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar medición")
            }
        }
    }
}