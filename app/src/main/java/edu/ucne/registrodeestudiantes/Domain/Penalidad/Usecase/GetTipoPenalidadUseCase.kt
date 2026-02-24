package edu.ucne.registrodeestudiantes.Domain.Penalidad.Usecase

import edu.ucne.registrodeestudiantes.Domain.Penalidad.Model.TipoPenalidad
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Repository.TipoPenalidadRepository
import javax.inject.Inject

class GetTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(id: Int): TipoPenalidad? {
        if (id <= 0) return null
        return repository.find(id)
    }
}
