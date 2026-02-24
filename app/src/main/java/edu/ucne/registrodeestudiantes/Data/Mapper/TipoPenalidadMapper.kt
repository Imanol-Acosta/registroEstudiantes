package edu.ucne.registrodeestudiantes.Data.Mapper

import edu.ucne.registrodeestudiantes.Data.Tareas.local.TipoPenalidadEntity
import edu.ucne.registrodeestudiantes.Domain.Penalidad.Model.TipoPenalidad

fun TipoPenalidadEntity.toDomain(): TipoPenalidad =
    TipoPenalidad(
        tipoId = tipoId,
        nombre = nombre,
        descripcion = descripcion,
        puntosDescuento = puntosDescuento
    )

fun TipoPenalidad.toEntity(): TipoPenalidadEntity =
    TipoPenalidadEntity(
        tipoId = tipoId,
        nombre = nombre,
        descripcion = descripcion,
        puntosDescuento = puntosDescuento
    )
