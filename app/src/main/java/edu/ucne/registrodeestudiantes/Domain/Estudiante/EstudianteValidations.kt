package edu.ucne.registrodeestudiantes.Domain.Estudiante

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)

fun validateNombres(nombres: String): ValidationResult {
    return when {
        nombres.isBlank() -> ValidationResult(false, "El nombre no puede estar vacío")
        nombres.length < 3 -> ValidationResult(false, "El nombre debe tener al menos 3 caracteres")
        else -> ValidationResult(true)
    }
}

fun validateEmail(email: String): ValidationResult {
    return when {
        email.isBlank() -> ValidationResult(false, "El email no puede estar vacío")
        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult(false, "El email no es válido")
        else -> ValidationResult(true)
    }
}

fun validateEdad(edad: Int?): ValidationResult {
    return when {
        edad == null -> ValidationResult(false, "La edad es requerida")
        edad <= 0 -> ValidationResult(false, "La edad debe ser mayor a 0")
        else -> ValidationResult(true)
    }
}
