package com.riezki.mvinote.note_list.domain.usecase

import com.riezki.mvinote.core.domain.model.NoteItem
import com.riezki.mvinote.core.domain.repository.NoteRepository

/**
 * @author riezkymaisyar
 */

class DeleteNotes(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: NoteItem) {
        repository.deleteNote(note)
    }
}