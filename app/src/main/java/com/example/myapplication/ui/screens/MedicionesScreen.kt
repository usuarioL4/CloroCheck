package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.local.MedicionEntity
import com.example.myapplication.ui.components.MedicionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicionesScreen(
    mediciones: List<MedicionEntity>,
    onRegistrar: () -> Unit,
    onVerDetalle: (Long) -> Unit,
    onVolver: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial") },
                navigationIcon = {
                    TextButton(onClick = onVolver) {
                        Text("Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onRegistrar) {
                Text("+")
            }
        }
    ) { padding ->
        if (mediciones.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("Todavía no hay mediciones registradas.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(
                    items = mediciones,
                    key = { it.id }
                ) { medicion ->
                    MedicionCard(
                        medicion = medicion,
                        onVerDetalle = {
                            onVerDetalle(medicion.id)
                        }
                    )
                }
            }
        }
    }
}