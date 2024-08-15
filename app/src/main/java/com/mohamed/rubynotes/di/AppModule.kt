package com.mohamed.rubynotes.di

import android.app.Application
import androidx.room.Room
import com.mohamed.rubynotes.data.AppDatabase
import com.mohamed.rubynotes.domain.NoteRepository
import com.mohamed.rubynotes.domain.NoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase{
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "RubyDB"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(appDB: AppDatabase): NoteRepository{
        return NoteRepositoryImpl(appDB.noteDao())
    }
}