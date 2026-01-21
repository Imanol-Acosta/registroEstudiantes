package com.example.registroEstudiante.Presentation.Estudiante.List

import com.example.registroEstudiante.Domain.Model.Estudiante

data class ListEstudianteUiState(
    val isLoading: Boolean = false,
    val estudiantes: List<Estudiante> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)