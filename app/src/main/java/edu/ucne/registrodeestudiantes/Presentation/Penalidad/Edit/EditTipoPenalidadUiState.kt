package edu.ucne.registrodeestudiantes.Presentation.Penalidad.Edit

data class EditTipoPenalidadUiState(
    val tipoId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val puntosDescuento: Int? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val saved: Boolean = false,
    val deleted: Boolean = false,
    val isNew: Boolean = true,
    val nombreError: String? = null,
    val descripcionError: String? = null,
    val puntosError: String? = null,
    val generalError: String? = null
)
