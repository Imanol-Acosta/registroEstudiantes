package edu.ucne.registrodeestudiantes.Domain.Estudiante.Usecase
import edu.ucne.registrodeestudiantes.Domain.Estudiante.Repository.EstudianteRepository


import javax.inject.Inject

class DeleteEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int) {
        if (id <= 0) throw IllegalArgumentException("El ID tiene que ser mayor que 0")
        repository.delete(id)
    }
}