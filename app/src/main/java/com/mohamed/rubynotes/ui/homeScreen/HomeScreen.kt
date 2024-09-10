
package com.mohamed.rubynotes.ui.homeScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mohamed.rubynotes.data.Note
import com.mohamed.rubynotes.ui.homeScreen.composables.HomeBottomBarSelected
import com.mohamed.rubynotes.ui.homeScreen.composables.HomeTopBar
import com.mohamed.rubynotes.ui.homeScreen.composables.HomeTopBarSelected
import com.mohamed.rubynotes.ui.homeScreen.composables.NoteGridCard
import com.mohamed.rubynotes.ui.navigation.AddEditNote

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel ,
    navController: NavController,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior
){
    val notes by viewModel.notesScreen.collectAsState()

    HomeScreenContent(notes = notes,
        navController,
        viewModel,
        modifier,
        scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    notes: HomeScreenState,
    navController: NavController,
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior
){
    var ascending by remember {
        mutableStateOf(false)
    }

    var orderType by remember {
        mutableStateOf("Date Modified")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val orderTypes = listOf(
        "Title",
        "Date Created",
        "Date Modified"
    )

    var isInSelectionMode by remember {
        mutableStateOf(false)
    }
    val selectedNotes = remember {
        mutableStateListOf<Note>()
    }

    val resetSelectionMode = {
        isInSelectionMode = false
        selectedNotes.clear()
    }

    fun selectAllNotes(){
        selectedNotes.clear()
        selectedNotes.addAll(notes.notes)
    }

    BackHandler(
        enabled = isInSelectionMode,
    ) {
        resetSelectionMode()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            if (!isInSelectionMode){
                FloatingActionButton(onClick = {
                    navController.navigate(AddEditNote(-1))
                }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add New Note Button")
                }
            }
        },

        floatingActionButtonPosition = FabPosition.End,

        topBar = {
            if (isInSelectionMode){
                HomeTopBarSelected(
                    scrollBehavior = scrollBehavior,
                    selectedItems = selectedNotes,
                    notes.notes.size,
                    selectAllItems = { selectAllNotes() }
                )
            } else{
                HomeTopBar(
                    scrollBehavior
                ) {
                    /*TODO*/
                }
            }
        },

        bottomBar = {
            if (isInSelectionMode){
                HomeBottomBarSelected(
                    onPinClick = {},
                    onLockClick = {}
                ) {
                  viewModel.deleteNote(selectedNotes)
                }
            }
        }

    ) { padding ->


        LazyVerticalStaggeredGrid(
            modifier = Modifier,
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 4.dp,
                end = 4.dp,
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()),
        ) {

            item(span = StaggeredGridItemSpan.FullLine) {

                Row(
                    modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {

                    Box {
                        TextButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Sort,
                                contentDescription = "Sort Icon"
                            )
                            Text(text = orderType)
                        }
                        DropdownMenu(
                            expanded = expanded, onDismissRequest = { expanded = false }) {
                            orderTypes.forEach {
                                DropdownMenuItem(
                                    text = { Text(text = it)},
                                    onClick = {
                                        viewModel.getAllNotes(it.replace(" ", "") +
                                        if (ascending) "Asc" else "Desc")
                                        orderType = it
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    VerticalDivider(modifier = Modifier
                        .height(20.dp)
                        .padding(start = 8.dp, end = 8.dp))

                    IconButton(onClick = {
                        ascending = !ascending
                        viewModel.getAllNotes(orderType.replace(" ", "") +
                                if (ascending) "Asc" else "Desc")
                    }) {
                        Icon(
                            imageVector = if (ascending){
                                Icons.Filled.ArrowUpward
                            } else{
                                   Icons.Filled.ArrowDownward
                                  },
                            contentDescription = "Sort Icon"
                        )
                    }

                }
            }

            items(notes.notes, key = {item -> item.noteId!! }) {note ->

                val isSelected = selectedNotes.contains(note)

                NoteGridCard(note = note,
                    isPinned =  note.isPinned,
                    isLocked =  note.isLocked,
                    isInSelectionMode = isInSelectionMode,
                    isSelected = isSelected,
                    onCardClick = {
                        if (isInSelectionMode) {
                            if (isSelected) {
                                selectedNotes.remove(note)
                            } else {
                                selectedNotes.add(note)
                            }
                        } else {
                            if (!note.isLocked){
                                navController.navigate(
                                    AddEditNote(noteId = note.noteId!!)
                                )
                            }
                        }
                    }
                ){
                    if (isInSelectionMode) {
                        if (isSelected) {
                            selectedNotes.remove(note)
                        } else {
                            selectedNotes.add(note)
                        }
                    } else {
                        isInSelectionMode = true
                        selectedNotes.add(note)
                    }
                }
            }
        }
    }
}

