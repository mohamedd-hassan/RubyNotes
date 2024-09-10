package com.mohamed.rubynotes.data

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import java.time.LocalDateTime


@Entity
data class Note(
    @PrimaryKey val noteId: Int? = null,
    val title: String?,
    val body: String,
    val dateCreated: LocalDateTime? = null,
    val dateModified: LocalDateTime? = null,
    val isPinned: Boolean,
    val isLocked: Boolean
)

@Database(
    entities = [
        Note::class
    ],
    version = 3,
    exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase(){
    abstract fun noteDao(): NoteDao
}
