package com.riezki.mvinote.note_list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.riezki.mvinote.R
import com.riezki.mvinote.core.domain.model.NoteItem

/**
 * @author riezkymaisyar
 */

@Composable
fun NoteListScreen(
    modifier: Modifier = Modifier,
    onNavigateToAddNote: () -> Unit,
    noteList: List<NoteItem>,
    onChangeOrder: () -> Unit,
    onDeleteNote: (NoteItem) -> Unit,
    orderByTitle: Boolean,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.notes, noteList.size),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onChangeOrder() }
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = if (orderByTitle) stringResource(R.string.t) else stringResource(R.string.d),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = if (orderByTitle) stringResource(R.string.sort_by_date)
                        else stringResource(R.string.sort_by_title)
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.testTag("add_note_fab"),
                onClick = onNavigateToAddNote
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add_a_note))
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                ),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(
                noteList,
                key = { it }
            ) {
                //item below
                ListNoteItem(
                    noteItem = it,
                    onDeleteNote = {
                        onDeleteNote(it)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ListNoteItem(
    noteItem: NoteItem,
    onDeleteNote: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(noteItem.imageUrl)
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = noteItem.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .width(130.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(vertical = 8.dp)
        ) {

            Text(
                text = noteItem.title,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 19.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = noteItem.description,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Icon(
            modifier = Modifier.clickable { onDeleteNote() },
            imageVector = Icons.Default.Clear,
            contentDescription = "Delete Note",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun ListItemPrev() {
    ListNoteItem(
        noteItem = NoteItem(
            title = "Title",
            description = "Description",
            imageUrl = "",
            dateAdded = 1L
        ),
        onDeleteNote = { /*TODO*/ })
}