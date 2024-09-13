package com.riezki.mvinote.add_note.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.riezki.mvinote.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author riezkymaisyar
 */

@Composable
fun AddNoteScreen(
    addNoteState: AddNoteState,
    noteSaved: State<Boolean>,
    onSave: () -> Unit,
    onUpdateImage: (AddNoteActions) -> Unit,
    onUpdateTitle: (AddNoteActions) -> Unit,
    onUpdateDescription: (AddNoteActions) -> Unit,
    onSaveNote: (AddNoteActions) -> Unit,
    onUpdateSearchQuery: (AddNoteActions) -> Unit,
    onPickImage: (AddNoteActions) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable { onUpdateImage(AddNoteActions.UpdateImagesDialogVisibility) }
                .testTag("image_field"),
            model = ImageRequest
                .Builder(context)
                .data(addNoteState.imageUrl)
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = addNoteState.imageSearchQuery,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("title_field"),
            value = addNoteState.title,
            onValueChange = { onUpdateTitle(AddNoteActions.UpdateTitle(it)) },
            label = {
                Text(text = stringResource(id = R.string.title))
            },
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("description_field"),
            value = addNoteState.description,
            onValueChange = { onUpdateDescription(AddNoteActions.UpdateDescription(it)) },
            label = {
                Text(text = stringResource(R.string.description))
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("save_button"),
            onClick = {
                onSaveNote(AddNoteActions.SaveNote)
                scope.launch {
                    delay(500L)
                    if (noteSaved.value) {
                        onSave()
                    } else {
                        Toast.makeText(
                            context,
                            R.string.error_saving_note,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        ) {
            Text(
                text = stringResource(R.string.save),
                fontSize = 17.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

    if (addNoteState.isImagesDialogShowing) {
        Dialog(
            onDismissRequest = {
                onUpdateImage(AddNoteActions.UpdateImagesDialogVisibility)
            }
        ) {
            ImagesDialogContent(
                addNoteState = addNoteState,
                onSearchQueryChange = {
                    onUpdateSearchQuery(AddNoteActions.UpdateImageSearchQuery(it))
                },
                onImageClick = {
                    onPickImage(AddNoteActions.PickImage(it))
                    onUpdateImage(AddNoteActions.UpdateImagesDialogVisibility)
                }
            )
        }
    }
}

@Composable
fun ImagesDialogContent(
    addNoteState: AddNoteState,
    onSearchQueryChange: (String) -> Unit,
    onImageClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .clip(RoundedCornerShape(26.dp))
            .background(MaterialTheme.colorScheme.background)
            .testTag("images_dialog_content"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("search_field"),
            value = addNoteState.imageSearchQuery,
            onValueChange = onSearchQueryChange,
            label = {
                Text(text = stringResource(id = R.string.search_image))
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (addNoteState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                itemsIndexed(addNoteState.images) { index, url ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .clickable { onImageClick(url) }
                            .testTag("image_item_$index"),
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(url)
                            .size(Size.ORIGINAL)
                            .build(),
                        contentDescription = stringResource(id = R.string.image),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}