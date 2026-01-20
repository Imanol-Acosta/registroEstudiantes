package com.example.registroEstudiante.Data.Tareas.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estudiantes")

data class EntityEstudiante(
    @PrimaryKey(autoGenerate = true)
    val estudianteId: Int = 0,
    val nombres: String,
    val email: String,
    val edad: Int
)