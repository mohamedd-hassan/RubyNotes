package com.mohamed.rubynotes.ui.screens.homeScreen

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mohamed.rubynotes.data.Note

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: Note,
    onCardClick: ()->Unit,
    viewModel: HomeScreenViewModel,
    onCardLongClick: ()->Unit
){
    Card(
        colors = CardDefaults.cardColors(
//            containerColor = listOf(Color.Cyan, Color.Yellow, Color.Gray).random()
            containerColor = Color.Yellow
        ),
        modifier = Modifier
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
                Text(text = note.title?:"title",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1)
                Divider(Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Gray)
                Text(text = note.body,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 4)
            }
            IconButton(onClick = {
                viewModel.deleteNote(note) },
                modifier = Modifier.align(Alignment.BottomEnd)) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete")
            }
        }
    }
}