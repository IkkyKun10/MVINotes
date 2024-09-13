package com.riezki.mvinote.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.riezki.mvinote.add_note.presentation.AddNoteScreen
import com.riezki.mvinote.add_note.presentation.AddNoteViewModel
import com.riezki.mvinote.core.presentation.ui.theme.MVINoteTheme
import com.riezki.mvinote.note_list.presentation.NoteListScreen
import com.riezki.mvinote.note_list.presentation.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVINoteTheme {
                NavigationHost()
            }
        }
    }

    @Composable
    fun NavigationHost(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController()
    ) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Screen.NoteList
        ) {
            composable<Screen.NoteList> {
                val viewModel = hiltViewModel<NoteListViewModel>()
                val noteListState by viewModel.noteListState.collectAsStateWithLifecycle()
                val orderByTitle by viewModel.orderByTitle.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = true) {
                    viewModel.loadNotes()
                }
                NoteListScreen(
                    modifier = modifier,
                    onNavigateToAddNote = { navController.navigate(Screen.AddNote) },
                    noteList = noteListState,
                    onChangeOrder = viewModel::changeOrder,
                    onDeleteNote = viewModel::deleteNote,
                    orderByTitle = orderByTitle
                )
            }

            composable<Screen.AddNote> {
                val viewModel = hiltViewModel<AddNoteViewModel>()

                val addNoteState by viewModel.addNoteState.collectAsStateWithLifecycle()
                val noteSaved = viewModel.notedSavedChannel.collectAsState()
                println("noteSaved: " + noteSaved.value)
                AddNoteScreen(
                    addNoteState = addNoteState,
                    noteSaved = noteSaved,
                    onSave = {
                        navController.popBackStack()
                    },
                    onUpdateImage = viewModel::onAction,
                    onUpdateTitle = viewModel::onAction,
                    onUpdateDescription = viewModel::onAction,
                    onSaveNote = viewModel::onAction,
                    onUpdateSearchQuery = viewModel::onAction,
                    onPickImage = viewModel::onAction
                )
            }
        }
    }
}