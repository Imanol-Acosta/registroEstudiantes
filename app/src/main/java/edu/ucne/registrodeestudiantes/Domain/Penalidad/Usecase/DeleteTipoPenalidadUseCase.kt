package edu.ucne.registrodeestudiantes.Domain.Penalidad.Usecase

import edu.ucne.registrodeestudiantes.Domain.Penalidad.Repository.TipoPenalidadRepository
import javax.inject.Inject

class DeleteTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.delete(id)
    }
}
