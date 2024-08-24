package com.mohamed.rubynotes.ui.addEditNote

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mohamed.rubynotes.ui.addEditNote.composables.NoteBottomRow
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun NoteScreen(
    viewModel: AddEditNoteViewModel,
    noteId: Int,
){

    viewModel.noteId = noteId

    viewModel.getNoteById(noteId)
    Log.d("State", noteId.toString() + "from NoteScreen")
    val noteState by viewModel.note.collectAsStateWithLifecycle()
    Log.d("State", noteState.noteBody + "from NoteScreen")

    NoteContent(
        state = noteState,
        noteId = noteId,
        viewModel = viewModel,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteContent(
    state: NoteState,
    noteId: Int,
    viewModel: AddEditNoteViewModel,
    ){

    var noteTitle by remember {
        mutableStateOf(state.noteTitle)
    }

    val noteBody = rememberRichTextState()

    var dateCreated = remember {
        LocalDateTime.now()
    }

    var dateModified = remember {
        LocalDateTime.now()
    }

    var isPinned by remember {
        mutableStateOf(false)
    }

    var isLocked by remember {
        mutableStateOf(false)
    }

    val formatter = DateTimeFormatter.ofPattern("d MMM uuuu hh:mm a")

    noteBody.config.listIndent = 15

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = state) {
        noteBody.setHtml(state.noteBody)
        noteTitle = state.noteTitle
        dateCreated = state.dateCreated
        dateModified = state.dateModified
        isPinned = state.isPinned
        isLocked = state.isLocked
    }


    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver{_, event ->
            when(event) {
                Lifecycle.Event.ON_PAUSE -> {
                    viewModel.insertNote(
                        noteTitle,
                        noteBody,
                        isPinned,
                        isLocked,
                        dateCreated,
                        noteId,
                        if (noteBody.toHtml() == state.noteBody && noteTitle == state.noteTitle) dateModified else LocalDateTime.now())
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

    Scaffold(
        modifier = Modifier.imePadding(),
        bottomBar = {
            NoteBottomRow(state = noteBody)
        }
    ){ it ->
        Column(modifier = Modifier
            .imePadding()
            .padding(paddingValues = it)) {
            TextField(value = noteTitle?:"",
                onValueChange = {noteTitle = it},
                Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Title")},
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Row (
                modifier = Modifier.padding(4.dp)
            ){
                Text(
                    text = dateModified.format(formatter),
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color.Gray,
                    fontSize = 12.sp)
                VerticalDivider(
                    modifier = Modifier
                        .height(20.dp)
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp)
                )
                Text(
                    text = noteBody.toMarkdown()
                        .replace(Regex("[\\s#\\*\\-_!\\[\\](){}\\+]"), "")
                        .length.toString() + " Characters",
                    color = Color.Gray,
                    fontSize = 12.sp)
            }
            RichTextEditor(state = noteBody,
                Modifier.fillMaxSize(),
                colors = RichTextEditorDefaults.richTextEditorColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}