package edu.ucne.registrodeestudiantes.Domain.Penalidad

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)

fun validateNombre(nombre: String): ValidationResult {
    return when {
        nombre.isBlank() -> ValidationResult(false, "El nombre no puede estar vacío")
        nombre.length < 3 -> ValidationResult(false, "El nombre debe tener al menos 3 caracteres")
        else -> ValidationResult(true)
    }
}

fun validateDescripcion(descripcion: String): ValidationResult {
    return when {
        descripcion.isBlank() -> ValidationResult(false, "La descripción no puede estar vacía")
        else -> ValidationResult(true)
    }
}

fun validatePuntos(puntos: Int?): ValidationResult {
    return when {
        puntos == null -> ValidationResult(false, "Los puntos son requeridos")
        puntos <= 0 -> ValidationResult(false, "Los puntos deben ser mayor a 0")
        else -> ValidationResult(true)
    }
}
