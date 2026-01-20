package com.example.registroEstudiante.Domain.Usecase
import com.example.registroEstudiante.Domain.Repository.EstudianteRepository


import javax.inject.Inject

class DeleteEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int) {
        if (id <= 0) throw IllegalArgumentException("El ID tiene que ser mayor que 0")
        repository.delete(id)
    }
}