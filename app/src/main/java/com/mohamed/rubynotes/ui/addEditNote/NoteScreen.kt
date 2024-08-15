package com.mohamed.rubynotes.ui.addEditNote

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.mohamed.rubynotes.ui.homeScreen.HomeScreenViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun NoteScreen(
    viewModel: HomeScreenViewModel,
    navController: NavController,
    noteId: Int,
){
    if(noteId == -1){
        NoteContent(title = "", body = "",noteId , viewModel, navController, viewModel.state)
    } else {
        val note = runBlocking { viewModel.getNoteById(noteId) }
        note.title?.let {
            NoteContent(title = it, body = note.body,noteId , viewModel, navController, viewModel.state)
        }
    }
}

@Composable
fun NoteContent(
    title: String,
    body:String,
    noteId: Int,
    viewModel: HomeScreenViewModel,
    navController: NavController,
    state: NoteScreenState){
    var noteTitle by remember {
        mutableStateOf(title)
    }
    val noteBody = rememberTextFieldState()
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = Unit) {
        noteBody.edit {
            insert(0, body)
        }
    }

    //val currentOnStop by rememberUpdatedState(onStop)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver{_, event ->
            when(event) {
                Lifecycle.Event.ON_CREATE ->{
                    Log.d("Thingy", "on Create")
                }

                Lifecycle.Event.ON_START -> {
                    Log.d("Thingy", "on Start")
                }
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("Thingy", "on Resume")
                }
                Lifecycle.Event.ON_PAUSE -> {
                    viewModel.insertNote(noteTitle, noteBody.text.toString(), noteId)
                }
                Lifecycle.Event.ON_STOP -> {

                }
                Lifecycle.Event.ON_DESTROY -> {
                    Log.d("Thingy", "on Destroy")
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
//        floatingActionButton = {
//            FloatingActionButton(onClick = {
//                if (noteBody.text == ""){
//                    Toast.makeText(context, "Note Body can't be empty", Toast.LENGTH_SHORT).show()
//                } else{
//                    viewModel.insertNote(noteTitle, noteBody.text.toString(), noteId)
//                    navController.navigate(HomeScreen)
//                }
//            }) {
//                Icon(imageVector = Icons.Default.Save, contentDescription = "Add New Note Button")
//            }
//        },
//        floatingActionButtonPosition = FabPosition.End
    ) { it ->
        Column(modifier = Modifier.padding(paddingValues = it)) {
            TextField(value = noteTitle,
                onValueChange = {noteTitle = it},
                Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Title")},
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
            Divider(thickness = 2.dp,
                color = Color.Gray)
            BasicTextField(state = noteBody,
                Modifier.fillMaxSize(),
            )
        }
    }
}