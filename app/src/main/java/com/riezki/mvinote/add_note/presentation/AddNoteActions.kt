package com.riezki.mvinote.add_note.presentation

/**
 * @author riezkymaisyar
 */

sealed interface AddNoteActions {
    data class UpdateTitle(val title: String) : AddNoteActions
    data class UpdateDescription(val description: String) : AddNoteActions
    data class UpdateImageSearchQuery(val query: String) : AddNoteActions
    data class PickImage(val imageUrl: String) : AddNoteActions

    data object UpdateImagesDialogVisibility : AddNoteActions
    data object SaveNote : AddNoteActions
}