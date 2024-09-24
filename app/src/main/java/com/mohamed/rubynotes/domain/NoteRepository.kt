package com.mohamed.rubynotes.domain

import com.mohamed.rubynotes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotesByTitle(): Flow<List<Note>>

    fun getAllNotesByTitleDesc(): Flow<List<Note>>

    fun getAllNotesByDateCreated(): Flow<List<Note>>

    fun getAllNotesByDateCreatedDesc(): Flow<List<Note>>

    fun getAllNotesByDateModified(): Flow<List<Note>>

    fun getAllNotesByDateModifiedDesc(): Flow<List<Note>>

    fun getLockedNotesByTitle(): Flow<List<Note>>

    fun getLockedNotesByTitleDesc(): Flow<List<Note>>

    fun getLockedNotesByDateCreated(): Flow<List<Note>>

    fun getLockedNotesByDateCreatedDesc(): Flow<List<Note>>

    fun getLockedNotesByDateModified(): Flow<List<Note>>

    fun getLockedNotesByDateModifiedDesc(): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun getNoteById(noteId: Int?): Note

    suspend fun isEmpty():Boolean

    suspend fun deleteNote(notes: List<Note>)

    suspend fun searchNotes(query: String): Flow<List<Note>>

    suspend fun searchLockedNotes(query: String): Flow<List<Note>>
}