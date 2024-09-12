package com.riezki.mvinote.core.presentation

/**
 * @author riezkymaisyar
 */

sealed interface Screen {
    @kotlinx.serialization.Serializable
    data object NoteList : Screen

    @kotlinx.serialization.Serializable
    data object AddNote : Screen
}