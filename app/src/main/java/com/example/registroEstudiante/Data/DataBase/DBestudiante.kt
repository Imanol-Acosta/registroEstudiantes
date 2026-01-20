package com.example.registroEstudiante.Data.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.registroEstudiante.Data.Tareas.local.EstudianteDao




@Database(entities =[EntityEstudiante::class],
    version = 1,
    exportSchema = false)

abstract class DBEstudiante: RoomDatabase(){

    abstract fun EstudianteDao(): EstudianteDao
}