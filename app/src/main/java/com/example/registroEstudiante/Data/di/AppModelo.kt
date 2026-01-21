package com.example.registroEstudiante.Data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.registroEstudiante.Data.Repository.EstudianteRepositoryImpl
import com.example.registroEstudiante.Data.Tareas.local.EstudianteDao
import com.example.registroEstudiante.Domain.Repository.EstudianteRepository
import com.example.registroEstudiante.Data.DataBase.DBEstudiante
import jakarta.inject.Singleton


@InstallIn(
    SingletonComponent::class)
@Module

object AppModule {
    @Provides
    @Singleton
    fun provideEstudianteDB(@ApplicationContext appContext: Context) : DBEstudiante{

        return Room.databaseBuilder(
            appContext,
            DBEstudiante::class.java,
            "EstudianteDB"
        ).fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideEstudianteDao(estudianteDB: DBEstudiante) : EstudianteDao{
        return estudianteDB.EstudianteDao()
    }

    @Provides
    @Singleton
    fun provideEstudianteRepositoryImpl(estudianteDao: EstudianteDao) : EstudianteRepositoryImpl{
        return EstudianteRepositoryImpl(estudianteDao)
    }

    @Provides
    @Singleton
    fun provideEstudianteRepository(impl: EstudianteRepositoryImpl): EstudianteRepository {
        return impl
    }
}