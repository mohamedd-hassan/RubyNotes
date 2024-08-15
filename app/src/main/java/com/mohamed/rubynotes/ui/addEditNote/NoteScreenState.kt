package com.mohamed.rubynotes.ui.addEditNote

import android.graphics.Bitmap

data class NoteScreenState(
    var isLoading: Boolean = false,
    var selectedPhoto: Bitmap? = null,
    val response: String = ""
)
