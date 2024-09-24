package com.mohamed.rubynotes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
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