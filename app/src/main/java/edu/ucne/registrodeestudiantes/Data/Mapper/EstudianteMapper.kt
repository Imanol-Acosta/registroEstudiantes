package edu.ucne.registrodeestudiantes.Data.Mapper

import edu.ucne.registrodeestudiantes.Domain.Estudiante.Model.Estudiante
import edu.ucne.registrodeestudiantes.Data.Tareas.local.EntityEstudiante

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