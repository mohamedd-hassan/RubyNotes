package com.mohamed.rubynotes.domain

import com.mohamed.rubynotes.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLockedNotes  @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(noteOrder: String = "DateModifiedDesc"): Flow<List<Note>> {
        return when(noteOrder){
            "TitleAsc" ->{
                noteRepository.getLockedNotesByTitle()
            }
            "TitleDesc" ->{
                noteRepository.getLockedNotesByTitleDesc()
            }
            "DateCreatedAsc" ->{
                noteRepository.getLockedNotesByDateCreated()
            }
            "DateCreatedDesc" ->{
                noteRepository.getLockedNotesByDateCreatedDesc()
            }
            "DateModifiedAsc" ->{
                noteRepository.getLockedNotesByDateModified()
            }
            "DateModifiedDesc" ->{
                noteRepository.getLockedNotesByDateModifiedDesc()
            }
            else ->{
                noteRepository.getLockedNotesByDateModifiedDesc()
            }
        }
    }
}