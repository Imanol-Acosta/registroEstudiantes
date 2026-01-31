package edu.ucne.registrodeestudiantes.Data.Tareas.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asignaturas")
data class AsignaturaEntity(
    @PrimaryKey(autoGenerate = true)
    val asignaturaId: Int? = null,
    val codigo: String,
    val nombre: String,
    val aula: String,
    val creditos: Int
)
