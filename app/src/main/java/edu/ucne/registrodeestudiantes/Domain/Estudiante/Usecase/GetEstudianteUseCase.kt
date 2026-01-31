package edu.ucne.registrodeestudiantes.Domain.Estudiante.Usecase
import edu.ucne.registrodeestudiantes.Domain.Estudiante.Model.Estudiante
import edu.ucne.registrodeestudiantes.Domain.Estudiante.Repository.EstudianteRepository
import javax.inject.Inject

class GetEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int): Estudiante? {
        if (id <= 0) throw IllegalArgumentException("El id debe ser mayor a 0")
        return repository.getEstudiante(id)
    }
}