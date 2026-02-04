package edu.ucne.registrodeestudiantes.Presentation.Asignatura.Edit

data class EditAsignaturaUiState(
    val asignaturaId: Int? = null,
    val codigo: String = "",
    val nombre: String = "",
    val aula: String = "",
    val creditos: String = "", // Keeping as String for input handling, converted to Int on save/validation
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isLoading: Boolean = false,
    val saved: Boolean = false,
    val deleted: Boolean = false,
    val isNew: Boolean = true,
    val codigoError: String? = null,
    val nombreError: String? = null,
    val aulaError: String? = null,
    val creditosError: String? = null,
    val generalError: String? = null
)
