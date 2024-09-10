package com.mohamed.rubynotes.domain

import com.mohamed.rubynotes.data.Note
import com.mohamed.rubynotes.data.NoteDao
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

    override suspend fun insertNote(note: Note) = noteDao.insertNote(note = note)

    override suspend fun getNoteById(noteId: Int?): Note = noteDao.getNoteById(noteId = noteId)

    override suspend fun isEmpty(): Boolean = noteDao.isEmpty()

    override suspend fun deleteNote(notes: List<Note>) {
        notes.forEach { note ->
            noteDao.deleteNote(note)
        }
    }
}