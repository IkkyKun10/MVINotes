package com.riezki.mvinote.core.data.mapper

import com.riezki.mvinote.core.data.local.NoteEntity
import com.riezki.mvinote.core.domain.model.NoteItem

/**
 * @author riezkymaisyar
 */

fun NoteItem.toNoteEntityForInsert(): NoteEntity {
    return NoteEntity(
        title = title,
        description = description,
        imageUrl = imageUrl,
        dateAdded = dateAdded,
    )
}

fun NoteItem.toNoteEntityForDelete(): NoteEntity {
    return NoteEntity(
        title = title,
        description = description,
        imageUrl = imageUrl,
        dateAdded = dateAdded,
        id = id
    )
}

fun NoteEntity.toNoteItem() : NoteItem {
    return NoteItem(
        title = title,
        description = description,
        imageUrl = imageUrl,
        dateAdded = dateAdded,
        id = id ?: 0
    )
}