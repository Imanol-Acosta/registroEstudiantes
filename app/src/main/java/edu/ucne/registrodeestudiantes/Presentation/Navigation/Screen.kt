package edu.ucne.registrodeestudiantes.Presentation.Navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object EstudianteList : Screen()

    @Serializable
    data class Estudiante(val estudianteId: Int) : Screen()

    @Serializable
    data object AsignaturaList : Screen()

    @Serializable
    data class Asignatura(val asignaturaId: Int) : Screen()

    @Serializable
    data object PenalidadList : Screen()

    @Serializable
    data class Penalidad(val penalidadId: Int) : Screen()
}
