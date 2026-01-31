package edu.ucne.registrodeestudiantes.Usecase
import edu.ucne.registrodeestudiantes.Model.Estudiante
import edu.ucne.registrodeestudiantes.Repository.EstudianteRepository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    operator fun invoke(): Flow<List<Estudiante>> {
        return repository.observeEstudiante()
    }
}