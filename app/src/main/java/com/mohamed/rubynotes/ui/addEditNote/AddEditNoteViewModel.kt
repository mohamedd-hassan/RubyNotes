package com.mohamed.rubynotes.ui.addEditNote

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.rubynotes.data.Note
import com.mohamed.rubynotes.domain.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditNoteViewModel @Inject
constructor(
    private val noteRepository: NoteRepository,
): ViewModel() {

    private val _note = MutableStateFlow(NoteState())
    val note = _note.asStateFlow()

    var noteId: Int? = null

    fun insertNote(
        title: String?,
        body: String,
        noteId: Int){
        viewModelScope.launch {
            if (title != "" || body != "<br>"){
                if (noteId == -1){
                    noteRepository.insertNote(
                        Note(
                            title = title,
                            body = body,
                            time = System.currentTimeMillis())
                    )
                } else{
                    noteRepository.insertNote(
                        Note(
                            noteId = noteId,
                            title = title,
                            body = body,
                            time = System.currentTimeMillis())
                    )
                }
            }
        }
    }

    fun getNoteById(
        noteId: Int
    ) {
        viewModelScope.launch {
            if(noteId == -1){
                Log.d("AddEdit", "Inside If")
                _note.update {
                    it.copy(
                        noteId = -1,
                        noteTitle = "",
                        noteBody = "",
                        timeModified = 0
                    )
                }
            }
            else{
                val noteRetrieved = noteRepository.getNoteById(noteId)
                Log.d("AddEdit", "Inside Else")
                _note.update {
                    it.copy(
                        noteId = noteRetrieved.noteId!!,
                        noteTitle = noteRetrieved.title,
                        noteBody = noteRetrieved.body,
                        timeModified = noteRetrieved.time
                    )
                }
            }
            Log.d("AddEdit", "Inside Else")
        }
    }
}