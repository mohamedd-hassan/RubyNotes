package com.mohamed.rubynotes.ui.addEditNote

import android.graphics.Bitmap

data class NoteState(
    var noteId: Int = -1,
    var noteTitle: String? = "",
    var noteBody: String = "",
    var timeModified: Long = 0,
    var isLoading: Boolean = false,
    var selectedPhoto: Bitmap? = null,
)
