package com.mohamed.rubynotes.domain

import com.mohamed.rubynotes.data.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
}