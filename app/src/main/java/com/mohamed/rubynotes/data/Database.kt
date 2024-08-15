package com.mohamed.rubynotes.data

import androidx.lifecycle.LiveData
import androidx.room.AutoMigration
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity
data class Note(
    @PrimaryKey val noteId: Int? = null,
    val title: String?,
    val body: String,
    val time: Long
)

@Dao
interface NoteDao{
    @Query("SELECT * FROM NOTE ORDER BY title ASC")
    fun getAllNotesByTitle(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE ORDER BY title DESC")
    fun getAllNotesByTitleDesc(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE ORDER BY time ASC")
    fun getAllNotesByTime(): Flow<List<Note>>

    @Query("SELECT * FROM NOTE ORDER BY time DESC")
    fun getAllNotesByTimeDesc(): Flow<List<Note>>

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Query("SELECT * FROM NOTE WHERE noteId = :noteId")
    suspend fun getNoteById(noteId: Int?): Note

    @Query("SELECT (SELECT COUNT(*) FROM Note) == 0")
    suspend fun isEmpty(): Boolean

    @Delete
    suspend fun deleteNote(note: Note)
}

@Database(
    entities = [
        Note::class
    ],
    version = 2,
    autoMigrations = [
        AutoMigration (from = 1, to = 2,
        )],
    exportSchema = true)
abstract class AppDatabase: RoomDatabase(){
    abstract fun noteDao(): NoteDao
}
