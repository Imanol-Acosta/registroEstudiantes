package edu.ucne.registrodeestudiantes.Data.Repository

import edu.ucne.registrodeestudiantes.Data.Mapper.toDomain
import edu.ucne.registrodeestudiantes.Data.Mapper.toEntity
import edu.ucne.registrodeestudiantes.Data.Tareas.local.TipoPenalidadDao
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Model.TipoPenalidad
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TipoPenalidadRepositoryImpl @Inject constructor(
    private val tipoPenalidadDao: TipoPenalidadDao
) : TipoPenalidadRepository {
    override fun getAll(): Flow<List<TipoPenalidad>> = tipoPenalidadDao.getAll().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun find(id: Int): TipoPenalidad? = tipoPenalidadDao.find(id)?.toDomain()

    override suspend fun findByNombre(nombre: String): TipoPenalidad? =
        tipoPenalidadDao.findByNombre(nombre)?.toDomain()

    override suspend fun upsert(tipoPenalidad: TipoPenalidad) =
        tipoPenalidadDao.upsert(tipoPenalidad.toEntity())

    override suspend fun delete(tipoPenalidad: TipoPenalidad) =
        tipoPenalidadDao.delete(tipoPenalidad.toEntity())

    override suspend fun delete(id: Int) = tipoPenalidadDao.delete(id)
}
