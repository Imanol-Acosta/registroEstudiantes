package edu.ucne.registrodeestudiantes.Presentation.Asignatura.List

import edu.ucne.registrodeestudiantes.Domain.Asignatura.Model.Asignatura

data class ListAsignaturaUiState(
    val isLoading: Boolean = false,
    val asignaturas: List<Asignatura> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)
