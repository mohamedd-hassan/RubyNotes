package com.mohamed.rubynotes.ui.homeScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mohamed.rubynotes.AddEditNote
import com.mohamed.rubynotes.data.Note
import com.mohamed.rubynotes.ui.homeScreen.composables.NoteCard

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
){
    val notes = viewModel.getNotesByTitle().observeAsState(emptyList()).value
    HomeScreenContent(notes = notes,
        navController,
        viewModel,
        modifier
        )
}

@Composable
fun HomeScreenContent(
    notes: List<Note>,
    navController: NavController,
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier
){
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(AddEditNote(noteId = -1))
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add New Note Button")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)) {
            items(notes){note ->
                NoteCard(note = note,
                    onCardClick = {
                        navController.navigate(AddEditNote(noteId = note.noteId?:-1))
                    },
                    viewModel,
                    onCardLongClick = { /*TODO*/},
                )
            }
        }
    }
}