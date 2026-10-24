package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.local.MedicionDatabase
import com.example.myapplication.data.repository.AlertaRepository
import com.example.myapplication.data.repository.MedicionRepository
import com.example.myapplication.ui.navigation.AppNavigation
import com.example.myapplication.ui.viewmodel.MedicionViewModel
import com.example.myapplication.ui.viewmodel.MedicionViewModelFactory
import kotlin.jvm.java

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = MedicionDatabase.obtenerInstancia(this)

        val repository = MedicionRepository(database.medicionDao()
)
        val alertaRepository = AlertaRepository(database.alertaDao())

        val factory = MedicionViewModelFactory(
            repository,
            alertaRepository
        )

        val viewModel = ViewModelProvider(
            this,
            factory
        )[MedicionViewModel::class.java]

        setContent {
            showw(viewModel)
        }
    }
}
@Composable
fun showw(viewModel: MedicionViewModel) {
    MaterialTheme {
        AppNavigation(viewModel = viewModel)
    }
}
