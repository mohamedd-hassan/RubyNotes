package com.mohamed.rubynotes.ui.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.rubynotes.data.Note
import com.mohamed.rubynotes.domain.GetNotes
import com.mohamed.rubynotes.domain.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val notes: GetNotes
): ViewModel() {

    init {
        getAllNotes("TitleAsc")
    }

    private val _notesScreen = MutableStateFlow(HomeScreenState())
    val notesScreen = _notesScreen.asStateFlow()
    fun getAllNotes(noteOrder: String){
        viewModelScope.launch {
            notes.invoke(noteOrder).collect {noteList ->
                _notesScreen.update {
                    it.copy(
                        notes = noteList,
                    )
                }
            }
        }
    }

    fun pinNote(
        notes: List<Note>
    ){
        viewModelScope.launch {
            notes.forEach { note ->
                noteRepository.insertNote(Note(
                    noteId = note.noteId,
                    title = note.title,
                    body = note.body,
                    isPinned = !note.isPinned,
                    isLocked = note.isLocked,
                    dateModified = note.dateModified,
                    dateCreated = note.dateCreated)
                )
            }
        }
    }

    fun deleteNote(notes: List<Note>){
        viewModelScope.launch {
            noteRepository.deleteNote(notes)
        }
    }
}