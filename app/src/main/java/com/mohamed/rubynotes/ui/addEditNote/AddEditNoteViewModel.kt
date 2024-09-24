package com.mohamed.rubynotes.ui.addEditNote

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.rubynotes.domain.model.Note
import com.mohamed.rubynotes.domain.NoteRepository
import com.mohamedrejeb.richeditor.model.RichTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
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
        body: RichTextState,
        isPinned: Boolean,
        isLocked: Boolean,
        dateCreated: LocalDateTime,
        noteId: Int,
        dateModified: LocalDateTime){
        viewModelScope.launch {
            if (title != "" || body.toMarkdown().isNotBlank()){
                if (noteId == -1){
                    noteRepository.insertNote(
                        Note(
                            title = title,
                            body = body.toHtml(),
                            dateCreated = LocalDateTime.now(),
                            dateModified = LocalDateTime.now(),
                            isPinned = false,
                            isLocked = false
                        )
                    )
                } else{
                    noteRepository.insertNote(
                        Note(
                            noteId = noteId,
                            title = title,
                            body = body.toHtml(),
                            dateCreated = dateCreated,
                            dateModified = dateModified,
                            isPinned = isPinned,
                            isLocked = isLocked
                        )
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
                        dateModified = LocalDateTime.MIN
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
                        dateModified = noteRetrieved.dateModified!!,
                        dateCreated = noteRetrieved.dateCreated!!,
                        isPinned = noteRetrieved.isPinned,
                        isLocked = noteRetrieved.isLocked
                    )
                }
            }
            Log.d("AddEdit", "Inside Else")
        }
    }
}