package edu.ucne.registrodeestudiantes.Domain.Estudiante.Repository
import edu.ucne.registrodeestudiantes.Domain.Estudiante.Model.Estudiante
import kotlinx.coroutines.flow.Flow

interface EstudianteRepository {

    fun observeEstudiante(): Flow<List<Estudiante>>

    suspend fun getEstudiante(id: Int): Estudiante?

    suspend fun upsert(estudiante: Estudiante):Int

    suspend fun delete(id: Int)

    suspend fun getEstudiantesByNombre(nombre: String): List<Estudiante>


}