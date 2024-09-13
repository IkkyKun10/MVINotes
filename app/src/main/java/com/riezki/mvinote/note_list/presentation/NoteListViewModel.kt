package com.riezki.mvinote.note_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riezki.mvinote.core.domain.model.NoteItem
import com.riezki.mvinote.note_list.domain.usecase.DeleteNotes
import com.riezki.mvinote.note_list.domain.usecase.GetAllNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author riezkymaisyar
 */

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getAllNotes: GetAllNotes,
    private val deleteNotes: DeleteNotes,
) : ViewModel() {

    private val _noteListState = MutableStateFlow<List<NoteItem>>(emptyList())
    val noteListState = _noteListState.asStateFlow()

    private val _orderByTitle = MutableStateFlow(false)
    val orderByTitle = _orderByTitle.asStateFlow()

    fun loadNotes() = viewModelScope.launch {
        getAllNotes(orderByTitle.value).collectLatest { notes ->
            _noteListState.update {
                return@update notes
            }
        }
    }

    fun deleteNote(noteItem: NoteItem) = viewModelScope.launch {
        deleteNotes(noteItem)
        loadNotes()
    }

    fun changeOrder() = viewModelScope.launch {
        _orderByTitle.update { !it }
        loadNotes()
    }
}