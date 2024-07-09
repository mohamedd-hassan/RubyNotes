package com.mohamed.rubynotes.ui.screens.addNote

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mohamed.rubynotes.HomeScreen
import com.mohamed.rubynotes.ui.screens.homeScreen.HomeScreenViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun NoteScreen(
    viewModel: HomeScreenViewModel,
    navController: NavController,
    noteId: Int
){
    if(noteId == -1){
        NoteContent(title = "", body = "",noteId , viewModel, navController)
    } else {
        val note = runBlocking { viewModel.getNoteById(noteId) }
        note.title?.let {
            NoteContent(title = it, body = note.body,noteId , viewModel, navController)
        }
    }
}

@Composable
fun NoteContent(title: String, body:String,noteId: Int, viewModel: HomeScreenViewModel, navController: NavController){
    var noteTitle by remember {
        mutableStateOf(title)
    }
    var noteBody by remember {
        mutableStateOf(body)
    }
    val context = LocalContext.current
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (noteBody == ""){
                    Toast.makeText(context, "Note Body can't be empty", Toast.LENGTH_SHORT).show()
                } else{
                    viewModel.insertNote(noteTitle, noteBody, noteId)
                    navController.navigate(HomeScreen)
                }
            }) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Add New Note Button")
            }
        },
        floatingActionButtonPosition = FabPosition.End
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
            TextField(value = noteBody,
                onValueChange = {noteBody = it},
                Modifier.fillMaxSize(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
        }
    }
}