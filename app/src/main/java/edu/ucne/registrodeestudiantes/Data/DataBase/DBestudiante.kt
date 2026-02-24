package edu.ucne.registrodeestudiantes.Data.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registrodeestudiantes.Data.Tareas.local.EstudianteDao
import edu.ucne.registrodeestudiantes.Data.Tareas.local.EntityEstudiante
import edu.ucne.registrodeestudiantes.Data.Tareas.local.AsignaturaDao
import edu.ucne.registrodeestudiantes.Data.Tareas.local.AsignaturaEntity
import edu.ucne.registrodeestudiantes.Data.Tareas.local.TipoPenalidadDao
import edu.ucne.registrodeestudiantes.Data.Tareas.local.TipoPenalidadEntity

@Database(entities = [EntityEstudiante::class, AsignaturaEntity::class, TipoPenalidadEntity::class],
    version = 3,
    exportSchema = false)
abstract class DBEstudiante: RoomDatabase(){
    abstract fun EstudianteDao(): EstudianteDao
    abstract fun asignaturaDao(): AsignaturaDao
    abstract fun tipoPenalidadDao(): TipoPenalidadDao
}
