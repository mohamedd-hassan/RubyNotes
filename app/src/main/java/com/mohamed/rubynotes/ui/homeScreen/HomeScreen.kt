
package com.mohamed.rubynotes.ui.homeScreen

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mohamed.rubynotes.ui.homeScreen.composables.NoteGridCard
import com.mohamed.rubynotes.ui.navigation.AddEditNote

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel ,
    navController: NavController,
    modifier: Modifier = Modifier
){
    val notes by viewModel.notesScreen.collectAsState()

    HomeScreenContent(notes = notes,
        navController,
        viewModel,
        modifier
        )
}

@Composable
fun HomeScreenContent(
    notes: HomeScreenState,
    navController: NavController,
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier
){
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(AddEditNote(-1))
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add New Note Button")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier,
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 4.dp,
                end = 4.dp,
                top = 4.dp,
                bottom = padding.calculateBottomPadding()),
        ) {
            items(notes.notes, key = {item -> item.noteId!! }) {note ->
                NoteGridCard(note = note,
                    isPinned =  note.isPinned,
                    isLocked =  note.isLocked,
                    onCardClick = {
                        if (!note.isLocked){
                            navController.navigate(
                                AddEditNote(noteId = note.noteId!!)
                            )
                        }
                    }
                ){

                }
            }
        }
    }
}