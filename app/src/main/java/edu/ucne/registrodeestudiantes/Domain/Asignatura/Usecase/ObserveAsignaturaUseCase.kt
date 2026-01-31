package edu.ucne.registrodeestudiantes.Domain.Asignatura.Usecase

import edu.ucne.registrodeestudiantes.Domain.Asignatura.Model.Asignatura
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    operator fun invoke(): Flow<List<Asignatura>> {
        return repository.getAll()
    }
}
