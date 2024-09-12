package com.riezki.mvinote.add_note.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riezki.mvinote.add_note.domain.usecase.SearchImagesUseCase
import com.riezki.mvinote.add_note.domain.usecase.UpsertNoteUseCase
import com.riezki.mvinote.add_note.utils.ErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author riezkymaisyar
 */

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val upsertNoteUseCase: UpsertNoteUseCase,
    private val searchImagesUseCase: SearchImagesUseCase,
) : ViewModel() {
    private var searchJob: Job? = null

    private val _addNoteState = MutableStateFlow(AddNoteState())
    val addNoteState = _addNoteState.asStateFlow()

    private val _notedSavedChannel = Channel<Boolean>()
    val notedSavedChannel = _notedSavedChannel.receiveAsFlow()

    fun onAction(actions: AddNoteActions) {
        when (actions) {
            is AddNoteActions.UpdateTitle -> {
                _addNoteState.update {
                    it.copy(title = actions.title)
                }
            }

            is AddNoteActions.UpdateDescription -> {
                _addNoteState.update {
                    it.copy(description = actions.description)
                }
            }

            is AddNoteActions.UpdateImageSearchQuery -> {
                _addNoteState.update {
                    it.copy(imageSearchQuery = actions.query)
                }
                searchImages(actions.query)
            }

            is AddNoteActions.PickImage -> {
                _addNoteState.update {
                    it.copy(imageUrl = actions.imageUrl)
                }
            }

            AddNoteActions.UpdateImagesDialogVisibility -> {
                _addNoteState.update {
                    it.copy(isImagesDialogShowing = !it.isImagesDialogShowing)
                }
            }

            AddNoteActions.SaveNote -> {
                viewModelScope.launch {
                    val isSaved = upsertNote(
                        title = addNoteState.value.title,
                        description = addNoteState.value.description,
                        imageUrl = addNoteState.value.imageUrl
                    )
                    _notedSavedChannel.send(isSaved)
                }
            }
        }
    }

    private suspend fun upsertNote(
        title: String,
        description: String,
        imageUrl: String,
    ) = upsertNoteUseCase(title, description, imageUrl)

    private fun searchImages(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            searchImagesUseCase(query)
                .collect { result ->
                    result
                        .onLoading {
                            _addNoteState.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }
                        .onSuccess { data ->
                            _addNoteState.update {
                                it.copy(
                                    isLoading = false,
                                    images = data?.images ?: emptyList()
                                )
                            }
                        }
                        .onError { errorType, message ->
                            _addNoteState.update {
                                it.copy(
                                    isLoading = false,
                                    messageError = message,
                                    errorType = errorType ?: ErrorType.UNKNOWN_EXCEPTION
                                )
                            }
                        }
                }
        }
    }
}