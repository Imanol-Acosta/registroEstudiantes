package edu.ucne.registrodeestudiantes.Domain.Asignatura.Usecase

import edu.ucne.registrodeestudiantes.Domain.Asignatura.Model.Asignatura
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Repository.AsignaturaRepository
import javax.inject.Inject

class UpsertAsignaturaUseCase @Inject constructor(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(asignatura: Asignatura): Result<Unit> {
        return runCatching {
            repository.upsert(asignatura)
        }
    }
}
