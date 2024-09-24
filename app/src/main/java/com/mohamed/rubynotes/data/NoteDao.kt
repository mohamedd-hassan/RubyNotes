package com.mohamed.rubynotes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mohamed.rubynotes.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao{
    @Query("SELECT * FROM NOTE WHERE isLocked is 0 ORDER BY isPinned DESC, title Asc")
    fun getAllNotesByTitle(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE isLocked is 0 ORDER BY isPinned DESC, title DESC")
    fun getAllNotesByTitleDesc(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE isLocked is 0 ORDER BY isPinned DESC, dateCreated ASC")
    fun getAllNotesByDateCreated(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE isLocked is 0 ORDER BY isPinned DESC, dateCreated DESC")
    fun getAllNotesByDateCreatedDesc(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE isLocked is 0 ORDER BY isPinned DESC, dateModified Asc")
    fun getAllNotesByDateModified(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE isLocked is 0 ORDER BY isPinned DESC, dateModified DESC")
    fun getAllNotesByDateModifiedDesc(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE isLocked is 1 ORDER BY isPinned DESC, title Asc")
    fun getLockedNotesByTitle(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE isLocked is 1 ORDER BY isPinned DESC, title DESC")
    fun getLockedNotesByTitleDesc(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE isLocked is 1 ORDER BY isPinned DESC, dateCreated ASC")
    fun getLockedNotesByDateCreated(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE isLocked is 1 ORDER BY isPinned DESC, dateCreated DESC")
    fun getLockedNotesByDateCreatedDesc(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE isLocked is 1 ORDER BY isPinned DESC, dateModified Asc")
    fun getLockedNotesByDateModified(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE isLocked is 1 ORDER BY isPinned DESC, dateModified DESC")
    fun getLockedNotesByDateModifiedDesc(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE isLocked is 0 AND (title LIKE '%' || :query || '%' OR body LIKE '%' || :query || '%')")
    fun searchNotes(query: String): Flow<List<Note>>

    @Query("SELECT * FROM NOTE WHERE isLocked is 1 AND (title LIKE '%' || :query || '%' OR body LIKE '%' || :query || '%')")
    fun searchLockedNotes(query: String): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM NOTE WHERE noteId = :noteId")
    suspend fun getNoteById(noteId: Int?): Note

    @Query("SELECT (SELECT COUNT(*) FROM Note) == 0")
    suspend fun isEmpty(): Boolean

    @Delete
    suspend fun deleteNote(note: Note)

}