package edu.ucne.registrodeestudiantes.Domain.Penalidad.Model

data class TipoPenalidad(
    val tipoId: Int? = null,
    val nombre: String,
    val descripcion: String,
    val puntosDescuento: Int
)
