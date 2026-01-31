package edu.ucne.registrodeestudiantes.Data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrodeestudiantes.Data.Repository.EstudianteRepositoryImpl
import edu.ucne.registrodeestudiantes.Data.Repository.AsignaturaRepositoryImpl
import edu.ucne.registrodeestudiantes.Data.Tareas.local.EstudianteDao
import edu.ucne.registrodeestudiantes.Data.Tareas.local.AsignaturaDao
import edu.ucne.registrodeestudiantes.Domain.Estudiante.Repository.EstudianteRepository
import edu.ucne.registrodeestudiantes.Domain.Asignatura.Repository.AsignaturaRepository
import edu.ucne.registrodeestudiantes.Data.DataBase.DBEstudiante
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideEstudianteDB(@ApplicationContext appContext: Context) : DBEstudiante{
        return Room.databaseBuilder(
            appContext,
            DBEstudiante::class.java,
            "EstudianteDB"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideEstudianteDao(estudianteDB: DBEstudiante) : EstudianteDao{
        return estudianteDB.estudianteDao()
    }

    @Provides
    @Singleton
    fun provideAsignaturaDao(estudianteDB: DBEstudiante) : AsignaturaDao{
        return estudianteDB.asignaturaDao()
    }

    @Provides
    @Singleton
    fun provideEstudianteRepository(impl: EstudianteRepositoryImpl): EstudianteRepository {
        return impl
    }

    @Provides
    @Singleton
    fun provideAsignaturaRepository(impl: AsignaturaRepositoryImpl): AsignaturaRepository {
        return impl
    }
}
