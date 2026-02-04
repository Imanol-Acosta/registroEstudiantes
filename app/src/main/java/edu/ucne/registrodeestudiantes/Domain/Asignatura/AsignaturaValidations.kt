package edu.ucne.registrodeestudiantes.Domain.Asignatura

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)

fun validateCodigo(codigo: String): ValidationResult {
    return when {
        codigo.isBlank() -> ValidationResult(false, "El código no puede estar vacío")
        else -> ValidationResult(true)
    }
}

fun validateNombre(nombre: String): ValidationResult {
    return when {
        nombre.isBlank() -> ValidationResult(false, "El nombre no puede estar vacío")
        nombre.length < 3 -> ValidationResult(false, "El nombre debe tener al menos 3 caracteres")
        else -> ValidationResult(true)
    }
}

fun validateAula(aula: String): ValidationResult {
    return when {
        aula.isBlank() -> ValidationResult(false, "El aula no puede estar vacía")
        else -> ValidationResult(true)
    }
}

fun validateCreditos(creditos: Int?): ValidationResult {
    return when {
        creditos == null -> ValidationResult(false, "Los créditos son requeridos")
        creditos <= 0 -> ValidationResult(false, "Los créditos deben ser mayor a 0")
        else -> ValidationResult(true)
    }
}
