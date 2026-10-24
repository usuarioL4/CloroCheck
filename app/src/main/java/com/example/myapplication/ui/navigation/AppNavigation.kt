package com.example.myapplication.ui.navigation
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.*
import com.example.myapplication.ui.viewmodel.MedicionViewModel
@Composable
fun AppNavigation(viewModel: MedicionViewModel) {
    val navController = rememberNavController()
    val mediciones by viewModel.mediciones.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") {
            InicioScreen(
                mediciones = mediciones,
                onVerMediciones = {
                    navController.navigate("mediciones")
                },
                onRegistrar = {
                    navController.navigate("registro")
                }
            )
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
        ) { entrada ->
            val id = entrada.arguments?.getLong("medicionId")
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
                    }
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No se encontró la medición.",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}