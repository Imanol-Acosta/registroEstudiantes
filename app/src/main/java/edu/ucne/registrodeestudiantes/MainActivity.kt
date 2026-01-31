package edu.ucne.registrodeestudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registrodeestudiantes.Presentation.Navigation.RegistroNavHost
import edu.ucne.registrodeestudiantes.ui.theme.RegistroEstudiantesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroEstudiantesTheme {
                val navController = rememberNavController()
                RegistroNavHost(navController)
            }
        }
    }
}
