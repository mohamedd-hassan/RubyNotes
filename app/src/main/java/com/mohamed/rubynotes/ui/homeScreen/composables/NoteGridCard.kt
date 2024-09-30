package com.mohamed.rubynotes.ui.homeScreen.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohamed.rubynotes.domain.model.Note
import com.mohamed.rubynotes.ui.theme.Typography
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteGridCard(
    modifier: Modifier = Modifier,
    note: Note,
    isPinned: Boolean,
    isInSelectionMode: Boolean,
    isSelected:Boolean,
    onCardClick: ()->Unit,
    onCardLongClick: ()->Unit
){

    val noteBody = rememberRichTextState()
    LaunchedEffect(key1 = Unit) {
        noteBody.setHtml(note.body)
    }
    Card(
        shape = RoundedCornerShape(5.72.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
            .combinedClickable(
                onClick = onCardClick,
                onLongClick = onCardLongClick
            )
            .padding(5.dp),
        border = if (isSelected) BorderStroke(0.75.dp, Color.Red) else null,
        elevation = if (isSelected) CardDefaults.cardElevation( defaultElevation = 6.dp) else CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                Row(
                    modifier.fillMaxWidth()
                ) {
                    AnimatedVisibility(isInSelectionMode) {
                        if (isInSelectionMode) {
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Rounded.CheckCircle,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Rounded.RadioButtonUnchecked,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                                )
                            }
                        }
                    }
                    Text(
                        text = if (note.title == "") "Title" else note.title!! ,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                            .weight(0.9f)
                            .padding(start = 4.dp, top = 6.dp, bottom = 8.dp),
                        style = MaterialTheme.typography.labelLarge
                    )
                    AnimatedVisibility (isPinned){
                        Icon(
                            imageVector = Icons.Outlined.PushPin,
                            contentDescription = "Pinned Note Icon",
                            modifier
                                .weight(0.1f)
                                .padding(5.dp),
                            tint = Color.DarkGray)
                    }
                }
                RichText(state = noteBody,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 8,
                    modifier = Modifier.padding(start = 4.dp, top = 8.dp, bottom = 16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NoteGridCardPreview(){
    Column(
        modifier = Modifier.background(Color(242, 242, 242)).fillMaxSize(),

    ) {
        NoteGridCard(
            note = Note(
                title = "Default Text",
                body = "today you need to downlaod the entire playlist and transfer it to your phone  transfer it to your phone",
                dateModified = null,
                dateCreated = null,
                isLocked = false,
                isPinned = false
            ),
            isPinned = false,
            isInSelectionMode = false,
            isSelected = false,
            onCardClick = {},
            onCardLongClick = {}
        )
        NoteGridCard(
            note = Note(
                title = "Default Text Default Text Default Text Default Text",
                body = "today you need to downlaod the entire playlist and transfer it to your phone  transfer it to your phone",
                dateModified = null,
                dateCreated = null,
                isLocked = false,
                isPinned = false
            ),
            isPinned = true,
            isInSelectionMode = false,
            isSelected = false,
            onCardClick = {},
            onCardLongClick = {}
        )
        NoteGridCard(
            note = Note(
                title = "Default Text",
                body = "today you need to downlaod the entire playlist and transfer it to your phone  transfer it to your phone",
                dateModified = null,
                dateCreated = null,
                isLocked = false,
                isPinned = false
            ),
            isPinned = false,
            isInSelectionMode = true,
            isSelected = false,
            onCardClick = {},
            onCardLongClick = {}
        )
        NoteGridCard(
            note = Note(
                title = "Default Text",
                body = "today you need to downlaod the entire playlist and transfer it to your phone  transfer it to your phone",
                dateModified = null,
                dateCreated = null,
                isLocked = false,
                isPinned = false
            ),
            isPinned = false,
            isInSelectionMode = true,
            isSelected = true,
            onCardClick = {},
            onCardLongClick = {}
        )
        NoteGridCard(
            note = Note(
                title = "Default Text",
                body =
                "today you need to downlaod the entire playlist and transfer it to your phone  transfer it to your phone" +
                        "today you need to downlaod the entire playlist and transfer it to your phone  transfer it to your phone" +
                        "today you need to downlaod the entire playlist and transfer it to your phone  transfer it to your phone" +
                        "today you need to downlaod the entire playlist and transfer it to your phone  transfer it to your phone" +
                        "today you need to downlaod the entire playlist and transfer it to your phone  transfer it to your phone" +
                        "today you need to downlaod the entire playlist and transfer it to your phone  transfer it to your phone" +
                        "today you need to downlaod the entire playlist and transfer it to your phone  transfer it to your phone",
                dateModified = null,
                dateCreated = null,
                isLocked = false,
                isPinned = false
            ),
            isPinned = true,
            isInSelectionMode = true,
            isSelected = true,
            onCardClick = {},
            onCardLongClick = {}
        )
    }
}