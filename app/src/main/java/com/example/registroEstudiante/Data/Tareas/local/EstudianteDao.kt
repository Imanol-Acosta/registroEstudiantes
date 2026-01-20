package com.example.registroEstudiante.Data.Tareas.local
import com.example.registroEstudiante.Data.Tareas.local.EntityEstudiante
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
@Dao
interface EstudianteDao {
    @Query("SELECT * FROM estudiantes ORDER BY estudianteId DESC")
    fun observerAll():Flow<List<EntityEstudiante>>

    @Query("SELECT * FROM estudiantes WHERE estudianteId = :id")
    suspend fun  getById(id: Int): EntityEstudiante?

    @Upsert
    suspend fun upsert(estudiante: EntityEstudiante): Long

    @Delete
    suspend fun delete(estudiante: EntityEstudiante)

    @Query("DELETE FROM estudiantes WHERE estudianteId = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM estudiantes WHERE nombres = :nombre")
    suspend fun getEstudiantesByNombre(nombre: String): List<EntityEstudiante>
}