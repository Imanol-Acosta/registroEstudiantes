package edu.ucne.registrodeestudiantes.Presentation.Estudiante.List

import edu.ucne.registrodeestudiantes.Domain.Estudiante.Model.Estudiante

data class ListEstudianteUiState(
    val isLoading: Boolean = false,
    val estudiantes: List<Estudiante> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)