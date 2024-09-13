package com.riezki.mvinote.add_note.presentation

import com.riezki.mvinote.add_note.utils.ErrorType

/**
 * @author riezkymaisyar
 */

data class AddNoteState(
    val title: String = "",
    val description: String = "",
    val images: List<String> = emptyList(),
    val imageSearchQuery: String = "",
    var isImagesDialogShowing: Boolean = false,
    val isLoading: Boolean = false,
    val imageUrl: String = "",
    val messageError: String? = "",
    val errorType: ErrorType = ErrorType.UNKNOWN_EXCEPTION
)