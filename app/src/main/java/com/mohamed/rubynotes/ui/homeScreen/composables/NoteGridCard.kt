package com.mohamed.rubynotes.ui.homeScreen.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteGridCard(
    modifier: Modifier = Modifier,
    note: Note,
    isPinned: Boolean,
    isLocked: Boolean,
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
        colors = if (isLocked) {CardDefaults.cardColors(
            containerColor = Color.Black,)
        } else{
            CardDefaults.cardColors(
                containerColor = Color(255,194,194),)
              },
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .combinedClickable(
                onClick = onCardClick,
                onLongClick = onCardLongClick
            )
            .padding(5.dp),
        //border = BorderStroke(2.dp, Color.Black),

    ) {
        Box(modifier = Modifier.fillMaxSize()){
            if (isInSelectionMode) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.TopStart).padding(8.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.align(Alignment.TopStart).padding(8.dp)
                    )
                }
            }
            if (isLocked){
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "Locked Note",
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.Center))
            } else{
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    Row(
                        modifier.fillMaxWidth()
                    ) {
                        if (note.title!= null){
                            Text(text = note.title,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
                                    .weight(0.8f)
                                    .padding(start = 4.dp, top = 4.dp, bottom = 8.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (isPinned){
                            Icon(
                                imageVector = Icons.Filled.PushPin,
                                contentDescription = "Pinned Note Icon",
                                modifier
                                    .weight(0.2f)
                                    .padding(5.dp),
                                tint = Color.DarkGray)
                        }
                    }
                    RichText(state = noteBody,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 8,
                        modifier = Modifier.padding(start = 4.dp, top = 8.dp)
                    )
                }
            }
        }
    }
}