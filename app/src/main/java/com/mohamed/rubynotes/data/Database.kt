package com.mohamed.rubynotes.data

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mohamed.rubynotes.domain.model.Note
import java.time.LocalDateTime




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
