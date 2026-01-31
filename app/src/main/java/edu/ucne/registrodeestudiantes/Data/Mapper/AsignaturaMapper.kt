package edu.ucne.registrodeestudiantes.Data.Mapper

import edu.ucne.registrodeestudiantes.Domain.Asignatura.Model.Asignatura
import edu.ucne.registrodeestudiantes.Data.Tareas.local.AsignaturaEntity

fun AsignaturaEntity.toDomain(): Asignatura =
    Asignatura(
        asignaturaId = asignaturaId,
        codigo = codigo,
        nombre = nombre,
        aula = aula,
        creditos = creditos
    )

fun Asignatura.toEntity(): AsignaturaEntity =
    AsignaturaEntity(
        asignaturaId = asignaturaId,
        codigo = codigo,
        nombre = nombre,
        aula = aula,
        creditos = creditos
    )
