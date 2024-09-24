package com.mohamed.rubynotes.ui.homeScreen

import com.mohamed.rubynotes.domain.model.Note

data class HomeScreenState(
    val notes: List<Note> = emptyList()
)