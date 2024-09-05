package com.riezki.mvinote.note_list.domain.usecase

import com.riezki.mvinote.core.domain.model.NoteItem
import com.riezki.mvinote.core.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author riezkymaisyar
 */

class GetAllNotes(
    private val repository: NoteRepository
) {
    operator fun invoke(isOrderByTitle: Boolean) : Flow<List<NoteItem>> {
        return if (isOrderByTitle) {
            repository.getNotes().map { it.sortedBy { note -> note.title.lowercase() } }
        } else {
            repository.getNotes().map { it.sortedBy { note -> note.dateAdded } }
        }
    }
}