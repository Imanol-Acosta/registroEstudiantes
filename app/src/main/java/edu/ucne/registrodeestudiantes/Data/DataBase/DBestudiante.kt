package edu.ucne.registrodeestudiantes.Data.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registrodeestudiantes.Data.Tareas.local.EstudianteDao
import edu.ucne.registrodeestudiantes.Data.Tareas.local.EntityEstudiante



@Database(entities =[EntityEstudiante::class],
    version = 1,
    exportSchema = false)

abstract class DBEstudiante: RoomDatabase(){

    abstract fun EstudianteDao(): EstudianteDao
}