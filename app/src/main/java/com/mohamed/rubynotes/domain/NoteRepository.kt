package com.mohamed.rubynotes.domain

import com.mohamed.rubynotes.data.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotesByTitle(): Flow<List<Note>>

    fun getAllNotesByTitleDesc(): Flow<List<Note>>

    fun getAllNotesByDateCreated(): Flow<List<Note>>

    fun getAllNotesByDateCreatedDesc(): Flow<List<Note>>

    fun getAllNotesByDateModified(): Flow<List<Note>>

    fun getAllNotesByDateModifiedDesc(): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun getNoteById(noteId: Int?): Note

    suspend fun isEmpty():Boolean

    suspend fun deleteNote(notes: List<Note>)
}