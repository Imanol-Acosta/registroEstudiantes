package com.example.registroEstudiante.Domain.Usecase
import com.example.registroEstudiante.Domain.Model.Estudiante
import com.example.registroEstudiante.Domain.Repository.EstudianteRepository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    operator fun invoke(): Flow<List<Estudiante>> {
        return repository.observeEstudiante()
    }
}