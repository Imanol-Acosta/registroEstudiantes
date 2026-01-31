package edu.ucne.registrodeestudiantes.Data.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registrodeestudiantes.Data.Tareas.local.EstudianteDao
import edu.ucne.registrodeestudiantes.Data.Tareas.local.EntityEstudiante
import edu.ucne.registrodeestudiantes.Data.Tareas.local.AsignaturaDao
import edu.ucne.registrodeestudiantes.Data.Tareas.local.AsignaturaEntity

@Database(entities = [EntityEstudiante::class, AsignaturaEntity::class],
    version = 2,
    exportSchema = false)
abstract class DBEstudiante: RoomDatabase(){
    abstract fun estudianteDao(): EstudianteDao
    abstract fun asignaturaDao(): AsignaturaDao
}
