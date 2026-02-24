package edu.ucne.registrodeestudiantes.Domain.Penalidad.Usecase

import edu.ucne.registrodeestudiantes.Domain.Penalidad.Model.TipoPenalidad
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    operator fun invoke(): Flow<List<TipoPenalidad>> {
        return repository.getAll()
    }
}
