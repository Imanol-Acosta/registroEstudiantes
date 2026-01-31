package edu.ucne.registrodeestudiantes.Domain.Asignatura.Usecase

import edu.ucne.registrodeestudiantes.Domain.Asignatura.Repository.AsignaturaRepository
import javax.inject.Inject

class ValidateAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    data class ValidationResult(
        val isValid: Boolean,
        val codigoError: String? = null,
        val nombreError: String? = null,
        val aulaError: String? = null,
        val creditosError: String? = null
    )

    suspend operator fun invoke(
        codigo: String,
        nombre: String,
        aula: String,
        creditos: String,
        currentId: Int? = null
    ): ValidationResult {
        val codigoError = if (codigo.isBlank()) "El código es requerido" else null
        
        val nombreError = when {
            nombre.isBlank() -> "El nombre es requerido"
            else -> {
                val existente = repository.findByNombre(nombre)
                if (existente != null && existente.asignaturaId != currentId) {
                    "Ya existe una asignatura registrada con este nombre"
                } else null
            }
        }

        val aulaError = if (aula.isBlank()) "El aula es requerida" else null
        
        val creditosError = when {
            creditos.isBlank() -> "Los créditos son requeridos"
            creditos.toIntOrNull() == null -> "Créditos no válidos"
            creditos.toInt() <= 0 -> "Los créditos deben ser mayores a 0"
            else -> null
        }

        return ValidationResult(
            isValid = codigoError == null && nombreError == null && aulaError == null && creditosError == null,
            codigoError = codigoError,
            nombreError = nombreError,
            aulaError = aulaError,
            creditosError = creditosError
        )
    }
}
