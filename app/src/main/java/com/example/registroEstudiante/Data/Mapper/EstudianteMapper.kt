package com.example.registroEstudiante.Data.Mapper

import com.example.registroEstudiante.Domain.Model.Estudiante
import com.example.registroEstudiante.Data.Tareas.local.EntityEstudiante

fun EntityEstudiante.toDomain():Estudiante=
    Estudiante(
        estudianteId = estudianteId,
        nombres = nombres,
        email = email,
        edad = edad
    )

fun Estudiante.toEntity():EntityEstudiante =
    EntityEstudiante(
        estudianteId = estudianteId,
        nombres = nombres,
        email = email,
        edad = edad
    )