package com.example.myapplication.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.*
import com.example.myapplication.ui.viewmodel.MedicionViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(viewModel: MedicionViewModel) {

    val navController = rememberNavController()

    val mediciones by viewModel.mediciones.collectAsStateWithLifecycle()
    val alertas by viewModel.alertasAbiertas.collectAsStateWithLifecycle()

    val entrada by navController.currentBackStackEntryAsState()
    val rutaActual = entrada?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Medición de cloro") },
                navigationIcon = {
                    if (rutaActual == "alertas") {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    }
                },
                actions = {
                    BadgedBox(
                        badge = {
                            if (alertas.isNotEmpty()) {
                                Badge(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                ) {
                                    Text(alertas.size.toString())
                                } }
                        }
                    ) {
                        IconButton(
                            onClick = {
                                if (rutaActual != "alertas") {
                                    navController.navigate("alertas") {
                                        launchSingleTop = true } }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Ver alertas"
                            ) }
                    } })
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = "inicio",
            modifier = Modifier.padding(padding)
        ) {

            composable("inicio") {
                InicioScreen(
                    mediciones = mediciones,
                    onVerMediciones = {
                        navController.navigate("mediciones")
                    },
                    onRegistrar = {
                        navController.navigate("registro")
                    },
                    totalAlertasAbiertas = alertas.size
                )
            }

            composable("alertas") {
                AlertasScreen(viewModel = viewModel)
            }

            composable("mediciones") {
                MedicionesScreen(
                    mediciones = mediciones,
                    onRegistrar = {
                        navController.navigate("registro")
                    },
                    onVerDetalle = { id ->
                        navController.navigate("detalle/$id")
                    },
                    onVolver = {
                        navController.popBackStack()
                    }
                )
            }

            composable("registro") {
                RegistroScreen(
                    onGuardar = { granja, punto, nivel ->
                        viewModel.agregarMedicion(
                            granja = granja,
                            punto = punto,
                            nivel = nivel
                        )
                        navController.popBackStack()
                    },
                    onVolver = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = "detalle/{medicionId}",
                arguments = listOf(
                    navArgument("medicionId") {
                        type = NavType.LongType
                    }
                )
            ) { entradaDetalle ->

                val id = entradaDetalle.arguments?.getLong("medicionId")
                val medicion = mediciones.find { it.id == id }

                if (medicion != null) {
                    DetalleScreen(
                        medicion = medicion,
                        onGuardarAccion = { accion ->
                            viewModel.guardarAccion(
                                medicion.id,
                                accion
                            )
                        },
                        onVolver = {
                            navController.popBackStack()
                        },
                        onEliminar = {
                            viewModel.eliminarMedicion(medicion.id)
                            navController.popBackStack()
                        }
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No se encontró la medición.")
                    }
                }
            }
        }
    }
}