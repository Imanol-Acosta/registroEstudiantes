package edu.ucne.registrodeestudiantes.Presentation.Asignatura

import edu.ucne.registrodeestudiantes.Domain.Asignatura.Model.Asignatura

data class AsignaturaUiState(
    val asignaturaId: Int? = null,
    val codigo: String = "",
    val nombre: String = "",
    val aula: String = "",
    val creditos: String = "",
    val error: String? = null,
    val success: Boolean = false,
    val asignaturas: List<Asignatura> = emptyList()
)
