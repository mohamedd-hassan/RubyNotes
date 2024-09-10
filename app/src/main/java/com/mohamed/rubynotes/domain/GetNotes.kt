package com.mohamed.rubynotes.domain

import com.mohamed.rubynotes.data.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotes @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(noteOrder: String = "DateModifiedDesc"): Flow<List<Note>> {
        return when(noteOrder){
            "TitleAsc" ->{
                noteRepository.getAllNotesByTitle()
            }
            "TitleDesc" ->{
                noteRepository.getAllNotesByTitleDesc()
            }
            "DateCreatedAsc" ->{
                noteRepository.getAllNotesByDateCreated()
            }
            "DateCreatedDesc" ->{
                noteRepository.getAllNotesByDateCreatedDesc()
            }
            "DateModifiedAsc" ->{
                noteRepository.getAllNotesByDateModified()
            }
            "DateModifiedDesc" ->{
                noteRepository.getAllNotesByDateModifiedDesc()
            }
            else ->{
                noteRepository.getAllNotesByDateModifiedDesc()
            }
        }
    }
}