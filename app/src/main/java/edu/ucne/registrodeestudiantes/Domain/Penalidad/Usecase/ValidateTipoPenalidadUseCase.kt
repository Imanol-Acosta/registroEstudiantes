package edu.ucne.registrodeestudiantes.Domain.Penalidad.Usecase

import edu.ucne.registrodeestudiantes.Domain.Penalidad.Repository.TipoPenalidadRepository
import javax.inject.Inject

class ValidateTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    data class ValidationResult(
        val isValid: Boolean,
        val nombreError: String? = null,
        val descripcionError: String? = null,
        val puntosError: String? = null
    )

    suspend operator fun invoke(
        nombre: String,
        descripcion: String,
        puntos: String,
        currentId: Int? = null
    ): ValidationResult {
        val nombreError = when {
            nombre.isBlank() -> "El nombre es requerido"
            else -> {
                val existente = repository.findByNombre(nombre)
                if (existente != null && existente.tipoId != currentId) {
                    "Ya existe un tipo de penalidad registrado con este nombre"
                } else null
            }
        }

        val descripcionError = if (descripcion.isBlank()) "La descripción es requerida" else null

        val puntosError = when {
            puntos.isBlank() -> "Los puntos son requeridos"
            puntos.toIntOrNull() == null -> "Puntos no válidos"
            puntos.toInt() <= 0 -> "El valor debe ser mayor a cero"
            else -> null
        }

        return ValidationResult(
            isValid = nombreError == null && descripcionError == null && puntosError == null,
            nombreError = nombreError,
            descripcionError = descripcionError,
            puntosError = puntosError
        )
    }
}
