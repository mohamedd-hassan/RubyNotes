package com.mohamed.rubynotes.ui.addEditNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.rubynotes.data.Note
import com.mohamed.rubynotes.domain.NoteDaoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(private val noteDaoImpl: NoteDaoImpl): ViewModel() {


    fun insertNote(
        title: String?,
        body: String,
        noteId: Int){
        viewModelScope.launch {
            if (noteId == -1){
                noteDaoImpl.insertNote(
                    Note(
                        title = title,
                        body = body,
                        time = System.currentTimeMillis())
                )
            } else{
                noteDaoImpl.insertNote(
                    Note(
                        noteId = noteId,
                        title = title,
                        body = body,
                        time = System.currentTimeMillis())
                )
            }
        }
    }

    suspend fun getNoteById(noteId: Int): Note {
        return noteDaoImpl.getNoteById(noteId)
    }
}