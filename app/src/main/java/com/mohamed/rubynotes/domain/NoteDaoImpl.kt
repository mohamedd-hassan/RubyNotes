package com.mohamed.rubynotes.domain

import androidx.lifecycle.LiveData
import com.mohamed.rubynotes.data.Note
import com.mohamed.rubynotes.data.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteDaoImpl(
    private val noteDao: NoteDao
) {
    fun getAllNotesByTitle(): LiveData<List<Note>> = noteDao.getAllNotesByTitle()

    fun getAllNotesByTitleDesc(): Flow<List<Note>> = noteDao.getAllNotesByTitleDesc()

    fun getAllNotesByTime(): Flow<List<Note>> = noteDao.getAllNotesByTime()

    fun getAllNotesByTimeDesc(): Flow<List<Note>> = noteDao.getAllNotesByTimeDesc()

    suspend fun insertNote(note: Note) = noteDao.insertNote(note = note)

    suspend fun getNoteById(noteId: Int?): Note = noteDao.getNoteById(noteId = noteId)

    suspend fun isEmpty():Boolean = noteDao.isEmpty()

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}