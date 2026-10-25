package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.local.MedicionEntity
import com.example.myapplication.model.EstadoCloro
import com.example.myapplication.ui.components.MedicionCard
import androidx.compose.foundation.layout.FlowRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicionesScreen(
    mediciones: List<MedicionEntity>,
    onRegistrar: () -> Unit,
    onVerDetalle: (Long) -> Unit,
    onVolver: () -> Unit
) {
    var granja by remember { mutableStateOf("") }
    var punto by remember { mutableStateOf("") }
    var estadoSeleccionado by remember { mutableStateOf("Todos") }

    val filtradas = mediciones.filter { medicion ->
        val coincideGranja =
            medicion.granja.contains(granja, ignoreCase = true)
        val coincidePunto =
            medicion.punto.contains(punto, ignoreCase = true)
        val estado = EstadoCloro.desdeNivel(medicion.nivel)

        val coincideEstado = when (estadoSeleccionado) {
            "Normal" -> estado == EstadoCloro.NORMAL
            "Advertencia" -> estado == EstadoCloro.ADVERTENCIA
            "Crítico" -> estado == EstadoCloro.CRITICO
            else -> true
        }

        coincideGranja && coincidePunto && coincideEstado
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Historial") },
                navigationIcon = {
                    TextButton(onClick = onVolver) {
                        Text("Volver") } }
            )
        },
        floatingActionButton = { FloatingActionButton(onClick = onRegistrar) {
            Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = granja,
                onValueChange = { granja = it },
                label = { Text("Filtrar por granja") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = punto,
                onValueChange = { punto = it },
                label = { Text("Filtrar por punto") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text(
                "Estado del cloro",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 12.dp, bottom = 6.dp)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Todos", "Normal", "Advertencia", "Crítico")
                    .forEach { estado ->
                        FilterChip(
                            selected = estadoSeleccionado == estado,
                            onClick = { estadoSeleccionado = estado },
                            label = { Text(estado) }
                        )
                    }
            }

            Spacer(Modifier.height(8.dp))

            Text("Resultados: ${filtradas.size}")

            if (filtradas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        if (mediciones.isEmpty()) {
                            "Todavía no hay mediciones registradas."
                        } else {
                            "No hay mediciones que coincidan."
                        })
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(items = filtradas, key = { it.id }
                    ) { medicion ->
                        MedicionCard(
                            medicion = medicion,
                            onVerDetalle = { onVerDetalle(medicion.id) }) } } }
        }
    }
}
