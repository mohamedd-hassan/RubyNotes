package com.mohamed.rubynotes.domain

import com.mohamed.rubynotes.data.NoteDao
import com.mohamed.rubynotes.domain.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao: NoteDao
): NoteRepository {
    override fun getAllNotesByTitle(): Flow<List<Note>> = noteDao.getAllNotesByTitle()

    override fun getAllNotesByTitleDesc(): Flow<List<Note>> = noteDao.getAllNotesByTitleDesc()

    override fun getAllNotesByDateCreated(): Flow<List<Note>> = noteDao.getAllNotesByDateCreated()

    override fun getAllNotesByDateCreatedDesc(): Flow<List<Note>> = noteDao.getAllNotesByDateCreatedDesc()

    override fun getAllNotesByDateModified(): Flow<List<Note>> = noteDao.getAllNotesByDateModified()

    override fun getAllNotesByDateModifiedDesc(): Flow<List<Note>> = noteDao.getAllNotesByDateModifiedDesc()

    override fun getLockedNotesByTitle(): Flow<List<Note>> = noteDao.getLockedNotesByTitle()

    override fun getLockedNotesByTitleDesc(): Flow<List<Note>> = noteDao.getLockedNotesByTitleDesc()

    override fun getLockedNotesByDateCreated(): Flow<List<Note>> = noteDao.getLockedNotesByDateCreated()

    override fun getLockedNotesByDateCreatedDesc(): Flow<List<Note>> = noteDao.getLockedNotesByDateCreatedDesc()

    override fun getLockedNotesByDateModified(): Flow<List<Note>> = noteDao.getLockedNotesByDateModified()

    override fun getLockedNotesByDateModifiedDesc(): Flow<List<Note>> = noteDao.getLockedNotesByDateModifiedDesc()

    override suspend fun searchNotes(query: String): Flow<List<Note>> = noteDao.searchNotes(query)

    override suspend fun searchLockedNotes(query: String): Flow<List<Note>> = noteDao.searchLockedNotes(query)

    override suspend fun insertNote(note: Note) = noteDao.insertNote(note = note)

    override suspend fun getNoteById(noteId: Int?): Note = noteDao.getNoteById(noteId = noteId)

    override suspend fun isEmpty(): Boolean = noteDao.isEmpty()

    override suspend fun deleteNote(notes: List<Note>) {
        notes.forEach { note ->
            noteDao.deleteNote(note)
        }
    }
}