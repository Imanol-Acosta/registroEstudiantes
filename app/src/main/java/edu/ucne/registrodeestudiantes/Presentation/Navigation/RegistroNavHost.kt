package edu.ucne.registrodeestudiantes.Presentation.Navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registrodeestudiantes.Presentation.Asignatura.List.AsignaturaListScreen
import edu.ucne.registrodeestudiantes.Presentation.Estudiante.List.EstudianteListScreen
import edu.ucne.registrodeestudiantes.Presentation.Estudiante.Edit.EditEstudianteScreen
import edu.ucne.registrodeestudiantes.Presentation.Penalidad.List.ListTipoPenalidadScreen
import kotlinx.coroutines.launch

@Composable
fun RegistroNavHost(
    navHostController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.EstudianteList
        ) {
            composable<Screen.EstudianteList> {
                EstudianteListScreen(
                    onDrawer = { scope.launch { drawerState.open() } },
                    goToEstudiante = { id -> navHostController.navigate(Screen.Estudiante(id)) },
                    createEstudiante = { navHostController.navigate(Screen.Estudiante(0)) }
                )
            }

            composable<Screen.Estudiante> {
                val args = it.toRoute<Screen.Estudiante>()
                EditEstudianteScreen(
                    estudianteId = args.estudianteId,
                    onNavigateBack = { navHostController.navigateUp() }
                )
            }

            composable<Screen.AsignaturaList> {
                AsignaturaListScreen(
                    onDrawer = { scope.launch { drawerState.open() } },
                    goToAsignatura = { id -> navHostController.navigate(Screen.Asignatura(id)) },
                    createAsignatura = { navHostController.navigate(Screen.Asignatura(0)) }
                )
            }

            composable<Screen.Asignatura> {
                val args = it.toRoute<Screen.Asignatura>()
                edu.ucne.registrodeestudiantes.Presentation.Asignatura.Edit.EditAsignaturaScreen(
                    asignaturaId = args.asignaturaId,
                    onNavigateBack = { navHostController.navigateUp() }
                )
            }

            composable<Screen.PenalidadList> {
                ListTipoPenalidadScreen(
                    onDrawer = { scope.launch { drawerState.open() } },
                    goToPenalidad = { id -> navHostController.navigate(Screen.Penalidad(id)) },
                    createPenalidad = { navHostController.navigate(Screen.Penalidad(0)) }
                )
            }

            composable<Screen.Penalidad> {
                val args = it.toRoute<Screen.Penalidad>()
                edu.ucne.registrodeestudiantes.Presentation.Penalidad.Edit.EditTipoPenalidadScreen(
                    penalidadId = args.penalidadId,
                    onNavigateBack = { navHostController.navigateUp() }
                )
            }
        }
    }
}
