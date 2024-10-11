package com.uvg.proyectoplataformas.di

import android.app.Application
import androidx.room.Room
import com.uvg.proyectoplataformas.fornotes.data.local.NoteDatabase
import com.uvg.proyectoplataformas.fornotes.data.local.NoteDatabase.Companion.DATABASE_NAME
import com.uvg.proyectoplataformas.fornotes.data.repository.DataRepository
import com.uvg.proyectoplataformas.fornotes.domain.repository.Repository
import com.uvg.proyectoplataformas.fornotes.domain.usecase.AddNoteUsecase
import com.uvg.proyectoplataformas.fornotes.domain.usecase.DeleteNoteUsecase
import com.uvg.proyectoplataformas.fornotes.domain.usecase.GetNoteByIdUsecase
import com.uvg.proyectoplataformas.fornotes.domain.usecase.GetNotesUsecase
import com.uvg.proyectoplataformas.fornotes.domain.usecase.NoteUsecases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = NoteDatabase::class.java,
            name = DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(database: NoteDatabase): Repository {
        return DataRepository(database.dao)
    }

    @Provides
    @Singleton
    fun provideNoteUsecases(repository: Repository): NoteUsecases {
        return NoteUsecases(
            getNotesUsecase = GetNotesUsecase(repository),
            deleteNoteUsecase = DeleteNoteUsecase(repository),
            addNoteUsecase = AddNoteUsecase(repository),
            getNoteByIdUsecase = GetNoteByIdUsecase(repository)
        )
    }

}