package com.example.registroEstudiante.Data.Repository
import
import com.example.registroEstudiante.Data.Tareas.local.EstudianteDao
import com.example.registroEstudiante.Data.Tareas.local.EntityEstudiante
import com.example.registroEstudiante.Domain.Model.Estudiante
import com.example.registroEstudiante.Domain.Repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class EstudianteRepositorylmpl @Inject constructor(
    private val estudianteDao: EstudianteDao
) : EstudianteRepository {

    override fun observeEstudiante(): Flow<List<Estudiante>> {
        return estudianteDao.observerAll().map { entities ->
            entities.map { it.toEstudiante() }
        }
    }

    override suspend fun getEstudiante(id: Int): Estudiante? {
        return estudianteDao.getById(id)?.toEstudiante()
    }

    override suspend fun upsert(estudiante: Estudiante): Int {
        val entity = Estudiante.toEntity()
        val result = EstudianteDao.upsert(entity)
        return if (estudiante.estudianteId == 0) result.toInt() else estudiante.estudianteId
    }

    override suspend fun delete(id: Int) {
        EstudianteDao.deleteById(id)
    }

    override suspend fun getEstudiantesByNombre(nombre: String): List<Estudiante> {
        return EstudianteDao.getEstudiantesByNombre(nombre).map { it.toEstudiante() }
    }
}

fun EntityEstudiante.toEstudiante(): Estudiante {
    return Estudiante(
        estudianteId = estudianteId,
        nombres = nombres,
        email = email,
        edad = edad
    )
}

fun Estudiante.toEntity(): EntityEstudiante {
    return EntityEstudiante(
        estudianteId = estudianteId,
        nombres = nombres,
        email = email,
        edad = edad
    )
}