package edu.ucne.registrodeestudiantes.Presentation.Penalidad

data class TipoPenalidadUiState(
    val tipoId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val puntosDescuento: String = "",
    val error: String? = null,
    val success: Boolean = false,
    val nombreError: String? = null,
    val descripcionError: String? = null,
    val puntosError: String? = null
)
