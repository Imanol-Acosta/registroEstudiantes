package edu.ucne.registrodeestudiantes.Data.Repository

import edu.ucne.registrodeestudiantes.Data.Mapper.toDomain
import edu.ucne.registrodeestudiantes.Data.Mapper.toEntity
import edu.ucne.registrodeestudiantes.Domain.Estudiante.Model.Estudiante
import edu.ucne.registrodeestudiantes.Domain.Estudiante.Repository.EstudianteRepository
import edu.ucne.registrodeestudiantes.Data.Tareas.local.EstudianteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class EstudianteRepositoryImpl @Inject constructor(
    private val estudianteDao: EstudianteDao
) : EstudianteRepository {

    override fun observeEstudiante(): Flow<List<Estudiante>> {
        return estudianteDao.observerAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getEstudiante(id: Int): Estudiante? {
        return estudianteDao.getById(id)?.toDomain()
    }

    override suspend fun upsert(estudiante: Estudiante): Int {
        val entity = estudiante.toEntity()
        val result = estudianteDao.upsert(entity)
        return if (estudiante.estudianteId == 0) result.toInt() else estudiante.estudianteId
    }

    override suspend fun delete(id: Int) {
        estudianteDao.deleteById(id)
    }

    override suspend fun getEstudiantesByNombre(nombre: String): List<Estudiante> {
        return estudianteDao.getEstudiantesByNombre(nombre).map { it.toDomain() }
    }
}
