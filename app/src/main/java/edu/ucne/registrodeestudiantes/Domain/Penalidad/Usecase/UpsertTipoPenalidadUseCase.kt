package edu.ucne.registrodeestudiantes.Domain.Penalidad.Usecase

import edu.ucne.registrodeestudiantes.Domain.Penalidad.Model.TipoPenalidad
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Repository.TipoPenalidadRepository
import javax.inject.Inject

class UpsertTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(tipoPenalidad: TipoPenalidad): Result<Unit> {
        return runCatching {
            repository.upsert(tipoPenalidad)
        }
    }
}
