package com.mohamed.rubynotes.ui.addEditNote

import android.graphics.Bitmap
import java.time.LocalDateTime

data class NoteState(
    var noteId: Int? = null,
    var noteTitle: String? = "",
    var noteBody: String = "",
    var dateCreated: LocalDateTime = LocalDateTime.MIN,
    var dateModified: LocalDateTime = LocalDateTime.MIN,
    var isPinned: Boolean = false,
    var isLocked: Boolean = false,
    var isLoading: Boolean = false,
    var selectedPhoto: Bitmap? = null,
)
