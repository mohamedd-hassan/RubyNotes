package com.mohamed.rubynotes.ui.screens.homeScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.mohamed.rubynotes.data.AppDatabase
import com.mohamed.rubynotes.data.Note
import com.mohamed.rubynotes.data.NoteDaoImpl
import kotlinx.coroutines.launch

class HomeScreenViewModel(application: Application): AndroidViewModel(application) {
    private val database: AppDatabase
    init {
        database = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "database"
        ).build()
    }
    private val noteDaoImpl = NoteDaoImpl(database.noteDao())

    fun getNotesByTitle(): LiveData<List<Note>> = noteDaoImpl.getAllNotesByTitle()

    fun insertNote(
        title: String?,
        body: String,
        noteId: Int){
        viewModelScope.launch {
            if (noteId == -1){
                noteDaoImpl.insertNote(
                    Note(
                        title = title,
                        body = body,
                        time = System.currentTimeMillis())
                )
            } else{
                noteDaoImpl.insertNote(
                    Note(
                        noteId = noteId,
                        title = title,
                        body = body,
                        time = System.currentTimeMillis())
                )
            }
        }
    }

    suspend fun getNoteById(noteId: Int): Note {
        return noteDaoImpl.getNoteById(noteId)
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteDaoImpl.deleteNote(note)
        }
    }
}