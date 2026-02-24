package edu.ucne.registrodeestudiantes.Data.Tareas.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TipoPenalidadDao {
    @Upsert
    suspend fun upsert(tipoPenalidad: TipoPenalidadEntity)

    @Delete
    suspend fun delete(tipoPenalidad: TipoPenalidadEntity)

    @Query("SELECT * FROM TiposPenalidades WHERE tipoId = :id")
    suspend fun find(id: Int): TipoPenalidadEntity?

    @Query("SELECT * FROM TiposPenalidades WHERE nombre = :nombre LIMIT 1")
    suspend fun findByNombre(nombre: String): TipoPenalidadEntity?

    @Query("SELECT * FROM TiposPenalidades")
    fun getAll(): Flow<List<TipoPenalidadEntity>>
}
