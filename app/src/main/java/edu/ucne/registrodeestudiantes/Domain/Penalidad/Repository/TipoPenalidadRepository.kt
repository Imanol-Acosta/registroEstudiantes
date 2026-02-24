package edu.ucne.registrodeestudiantes.Domain.Penalidad.Repository

import edu.ucne.registrodeestudiantes.Domain.Penalidad.Model.TipoPenalidad
import kotlinx.coroutines.flow.Flow

interface TipoPenalidadRepository {
    fun getAll(): Flow<List<TipoPenalidad>>
    suspend fun find(id: Int): TipoPenalidad?
    suspend fun findByNombre(nombre: String): TipoPenalidad?
    suspend fun upsert(tipoPenalidad: TipoPenalidad)
    suspend fun delete(tipoPenalidad: TipoPenalidad)
}
