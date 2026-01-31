package edu.ucne.registrodeestudiantes.Data.Tareas.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AsignaturaDao {
    @Upsert
    suspend fun upsert(asignatura: AsignaturaEntity): Long

    @Delete
    suspend fun delete(asignatura: AsignaturaEntity): Int

    @Query("SELECT * FROM asignaturas WHERE asignaturaId = :id")
    suspend fun find(id: Int): AsignaturaEntity?

    @Query("SELECT * FROM asignaturas WHERE nombre = :nombre LIMIT 1")
    suspend fun findByNombre(nombre: String): AsignaturaEntity?

    @Query("SELECT * FROM asignaturas")
    fun getAll(): Flow<List<AsignaturaEntity>>
}
