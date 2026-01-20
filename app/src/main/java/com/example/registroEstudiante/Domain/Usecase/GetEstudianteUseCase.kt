package com.example.registroEstudiante.Domain.Usecase
import com.example.registroEstudiante.Domain.Model.Estudiante
import com.example.registroEstudiante.Domain.Repository.EstudianteRepository
import javax.inject.Inject

class GetEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int): Estudiante? {
        if (id <= 0) throw IllegalArgumentException("El id debe ser mayor a 0")
        return repository.getEstudiante(id)
    }
}