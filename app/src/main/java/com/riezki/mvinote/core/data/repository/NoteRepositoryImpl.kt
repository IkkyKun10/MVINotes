package com.riezki.mvinote.core.data.repository

import com.riezki.mvinote.core.data.local.NoteDb
import com.riezki.mvinote.core.data.mapper.toNoteEntityForDelete
import com.riezki.mvinote.core.data.mapper.toNoteEntityForInsert
import com.riezki.mvinote.core.data.mapper.toNoteItem
import com.riezki.mvinote.core.domain.model.NoteItem
import com.riezki.mvinote.core.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * @author riezkymaisyar
 */

class NoteRepositoryImpl(
    noteDb: NoteDb
) : NoteRepository {
    private val dao = noteDb.noteDao

    override suspend fun upsertNote(note: NoteItem) {
        dao.upsertNote(note.toNoteEntityForInsert())
    }

    override suspend fun deleteNote(note: NoteItem) {
        dao.deleteNote(note.toNoteEntityForDelete())
    }

    override fun getNotes(): Flow<List<NoteItem>> {
        return dao.getNotes().map { notes -> notes.map { it.toNoteItem() } }
    }
}