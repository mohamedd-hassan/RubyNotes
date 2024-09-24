package com.mohamed.rubynotes.ui.homeScreen

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.rubynotes.domain.model.Note
import com.mohamed.rubynotes.domain.GetLockedNotes
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
    private val notes: GetNotes,
    private val vaultNotes: GetLockedNotes
): ViewModel() {

    init {
        getAllNotes("TitleAsc")
        getLockedNotes("TitleAsc")
    }

    private val _notesScreen = MutableStateFlow(HomeScreenState())

    val notesScreen = _notesScreen.asStateFlow()

    private val _vaultScreen = MutableStateFlow(HomeScreenState())

    val vaultScreen = _vaultScreen.asStateFlow()

    private val _searchResults = MutableStateFlow(HomeScreenState())

    val searchResults = _searchResults.asStateFlow()

    fun searchNotes(
        query: String,
        inVault: Boolean
    ){
        if (inVault){
            viewModelScope.launch {
                noteRepository.searchLockedNotes(query).collect { noteList ->
                    _searchResults.update {
                        it.copy(
                            notes = noteList,
                        )
                    }
                }
            }
        } else {
            viewModelScope.launch {
                noteRepository.searchNotes(query).collect { noteList ->
                    _searchResults.update {
                        it.copy(
                            notes = noteList,
                            )
                    }
                }
            }
        }
    }

    fun getAllNotes(noteOrder: String){
        viewModelScope.launch {
            notes.invoke(noteOrder).collect { noteList ->
                _notesScreen.update {
                    it.copy(
                        notes = noteList,
                    )
                }
            }
        }
    }

    fun getLockedNotes(noteOrder: String){
        viewModelScope.launch {
            vaultNotes.invoke(noteOrder).collect { noteList ->
                _vaultScreen.update {
                    it.copy(
                        notes = noteList,
                    )
                }
            }
        }
    }

    fun pinNote(
        notes: List<Note>,
        resetSelectionMode: () -> Unit
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
            resetSelectionMode()
        }
    }

    fun deleteNote(
        notes: List<Note>,
        resetSelectionMode: () -> Unit){
        viewModelScope.launch {
            noteRepository.deleteNote(notes)
            resetSelectionMode()
        }
    }

    fun lockNote(
        notes: List<Note>,
        resetSelectionMode: () -> Unit
    ){
        viewModelScope.launch {
            notes.forEach { note ->
                noteRepository.insertNote(Note(
                    noteId = note.noteId,
                    title = note.title,
                    body = note.body,
                    isPinned = note.isPinned,
                    isLocked = !note.isLocked,
                    dateModified = note.dateModified,
                    dateCreated = note.dateCreated)
                )
            }
            resetSelectionMode()
        }
    }
}