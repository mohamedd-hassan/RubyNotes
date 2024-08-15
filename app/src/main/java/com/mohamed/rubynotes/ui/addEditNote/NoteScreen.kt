package com.mohamed.rubynotes.ui.addEditNote

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor

@Composable
fun NoteScreen(
    viewModel: AddEditNoteViewModel,
    navController: NavController,
    noteId: Int,
){
    LaunchedEffect(Unit) {
        viewModel.getNoteById(noteId)
    }

    val noteState by viewModel.note.collectAsState()

    NoteContent(
        state = noteState,
        noteId = noteId,
        viewModel = viewModel,
        navController = navController
    )
}

@Composable
fun NoteContent(
    state: NoteState,
    noteId: Int,
    viewModel: AddEditNoteViewModel,
    navController: NavController,
    ){


    var noteTitle by remember {
        mutableStateOf(state.noteTitle)
    }

    val noteBody = rememberRichTextState()

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = Unit) {
        noteBody.setText(state.noteBody)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver{_, event ->
            when(event) {
                Lifecycle.Event.ON_PAUSE -> {
                    viewModel.insertNote(noteTitle, noteBody.toMarkdown(), noteId)
                }
                else -> {
                    Log.d("Thingy", "on else")
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold{ it ->
        Column(modifier = Modifier.padding(paddingValues = it)) {
            TextField(value = noteTitle?:"",
                onValueChange = {noteTitle = it},
                Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Title")},
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = Color.Gray
            )
            RichTextEditor(state = noteBody,
                Modifier.fillMaxSize(),
            )
        }
    }
}