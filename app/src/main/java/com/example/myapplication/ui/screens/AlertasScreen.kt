package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.local.AlertaEntity
import com.example.myapplication.model.EstadoCloro
import com.example.myapplication.ui.viewmodel.MedicionViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AlertasScreen(viewModel: MedicionViewModel) {

    val alertas by viewModel.alertasAbiertas.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Alertas abiertas",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (alertas.isEmpty()) {
            Text("No hay alertas abiertas.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(alertas) { alerta ->
                    AlertaCard(alerta)
                } } }
    }
}

@Composable
private fun AlertaCard(alerta: AlertaEntity) {

    val fecha = SimpleDateFormat(
        "dd/MM/yyyy HH:mm",
        Locale.getDefault()
    ).format(Date(alerta.fechaCreacion))

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) { Text(
            text = EstadoCloro.mensajeDesdeNivel(alerta.nivel),
            style = MaterialTheme.typography.titleMedium
        )
            Text("Granja: ${alerta.granja}")
            Text("Punto: ${alerta.punto}")
            Text("Nivel de cloro: ${alerta.nivel}")
        }
    }
}