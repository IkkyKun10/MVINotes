package com.riezki.mvinote.core.domain.repository

import com.riezki.mvinote.core.domain.model.NoteItem
import kotlinx.coroutines.flow.Flow

/**
 * @author riezkymaisyar
 */

interface NoteRepository {
    suspend fun upsertNote(note: NoteItem)
    suspend fun deleteNote(note: NoteItem)
    fun getNotes(): Flow<List<NoteItem>>
}
