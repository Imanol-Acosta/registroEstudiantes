package edu.ucne.registrodeestudiantes.Domain.Asignatura.Repository

import edu.ucne.registrodeestudiantes.Domain.Asignatura.Model.Asignatura
import kotlinx.coroutines.flow.Flow

interface AsignaturaRepository {
    fun getAll(): Flow<List<Asignatura>>
    suspend fun find(id: Int): Asignatura?
    suspend fun findByNombre(nombre: String): Asignatura?
    suspend fun upsert(asignatura: Asignatura)
    suspend fun delete(asignatura: Asignatura)
    suspend fun delete(id: Int)
}
