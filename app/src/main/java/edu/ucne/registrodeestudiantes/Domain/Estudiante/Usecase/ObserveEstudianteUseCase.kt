package edu.ucne.registrodeestudiantes.Domain.Estudiante.Usecase
import edu.ucne.registrodeestudiantes.Domain.Estudiante.Model.Estudiante
import edu.ucne.registrodeestudiantes.Domain.Estudiante.Repository.EstudianteRepository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    operator fun invoke(): Flow<List<Estudiante>> {
        return repository.observeEstudiante()
    }
}