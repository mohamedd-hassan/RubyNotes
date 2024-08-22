package com.mohamed.rubynotes.ui.homeScreen.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mohamed.rubynotes.data.Note
import com.mohamed.rubynotes.ui.homeScreen.HomeScreenViewModel
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: Note,
    onCardClick: ()->Unit,
    viewModel: HomeScreenViewModel,
    onCardLongClick: ()->Unit,
    modifier: Modifier = Modifier
){
    val noteBody = rememberRichTextState()
    LaunchedEffect(key1 = Unit) {
        noteBody.setHtml(note.body)
    }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(244,194,194)
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .combinedClickable(
                onClick = onCardClick,
                onLongClick = onCardLongClick
            )
            .padding(5.dp)

    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                Text(text = note.title?:"",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp, bottom = 8.dp),
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Gray
                )
                RichText(state = noteBody,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 8,
                    modifier = Modifier.padding(start = 4.dp, top = 8.dp))
            }
            IconButton(onClick = {
                viewModel.deleteNote(note) },
                modifier = Modifier.align(Alignment.BottomEnd)) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete", tint = Color.Black)
            }
        }
    }
}