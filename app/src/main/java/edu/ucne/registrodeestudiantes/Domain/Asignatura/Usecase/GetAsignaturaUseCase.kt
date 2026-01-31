package edu.ucne.registrodeestudiantes.Domain.Asignatura.Usecase

import edu.ucne.registrodeestudiantes.Domain.Asignatura.Model.Asignatura
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Repository.AsignaturaRepository
import javax.inject.Inject

class GetAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int): Asignatura? {
        if (id <= 0) throw IllegalArgumentException("El id debe ser mayor a 0")
        return repository.find(id)
    }
}
