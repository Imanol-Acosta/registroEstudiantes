package edu.ucne.registrodeestudiantes.Data.Repository

import edu.ucne.registrodeestudiantes.Data.Mapper.toDomain
import edu.ucne.registrodeestudiantes.Data.Mapper.toEntity
import edu.ucne.registrodeestudiantes.Data.Tareas.local.AsignaturaDao
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Model.Asignatura
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AsignaturaRepositoryImpl @Inject constructor(
    private val asignaturaDao: AsignaturaDao
) : AsignaturaRepository {
    override fun getAll(): Flow<List<Asignatura>> = asignaturaDao.getAll().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun find(id: Int): Asignatura? = asignaturaDao.find(id)?.toDomain()

    override suspend fun findByNombre(nombre: String): Asignatura? =
        asignaturaDao.findByNombre(nombre)?.toDomain()

    override suspend fun upsert(asignatura: Asignatura) {
        asignaturaDao.upsert(asignatura.toEntity())
    }

    override suspend fun delete(asignatura: Asignatura) {
        asignaturaDao.delete(asignatura.toEntity())
    }

    override suspend fun delete(id: Int) {
        asignaturaDao.delete(id)
    }
}
