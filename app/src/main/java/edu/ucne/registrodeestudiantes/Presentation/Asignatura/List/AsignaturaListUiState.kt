package edu.ucne.registrodeestudiantes.Presentation.Asignatura.List

import edu.ucne.registrodeestudiantes.Domain.Asignatura.Model.Asignatura

data class AsignaturaListUiState(
    val asignaturas: List<Asignatura> = emptyList(),
    val isLoading: Boolean = false
)
