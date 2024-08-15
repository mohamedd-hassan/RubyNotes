package com.mohamed.rubynotes.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
data class AddEditNote(
    val noteId:Int
)

@Serializable
object Settings