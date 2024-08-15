package com.mohamed.rubynotes.ui.homeScreen

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
class HomeScreenViewModel @Inject constructor(
    private val noteRepository: NoteRepository
): ViewModel() {

    init {
        getNotesByTitle()
    }

    private val _notesScreen = MutableStateFlow(HomeScreenState())
    val notesScreen = _notesScreen.asStateFlow()
    private fun getNotesByTitle(){
        viewModelScope.launch {
            noteRepository.getAllNotesByTitle().collect {noteList ->
                _notesScreen.update {
                    it.copy(
                        notes = noteList,
                    )
                }
            }
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }
}