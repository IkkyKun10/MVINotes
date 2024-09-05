package com.riezki.mvinote.add_note.domain.usecase

import com.riezki.mvinote.core.domain.model.NoteItem
import com.riezki.mvinote.core.domain.repository.NoteRepository

/**
 * @author riezkymaisyar
 */

class UpsertNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        imageUrl: String,
    ) : Boolean {
        if (title.isBlank() || description.isBlank()) {
            return false
        }

        val note = NoteItem(
            title = title,
            description = description,
            imageUrl = imageUrl,
            dateAdded = System.currentTimeMillis(),
        )

        repository.upsertNote(note)
        return true
    }
}