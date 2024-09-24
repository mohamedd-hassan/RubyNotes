package com.mohamed.rubynotes.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mohamed.rubynotes.R
import com.mohamed.rubynotes.domain.model.Note
import com.mohamed.rubynotes.ui.homeScreen.HomeScreenViewModel
import com.mohamed.rubynotes.ui.homeScreen.composables.NoteGridCard
import com.mohamed.rubynotes.ui.homeScreen.composables.ToggleListNotes
import com.mohamed.rubynotes.ui.navigation.AddEditNote
import com.mohamed.rubynotes.ui.theme.poppinsFamily

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel,
    inVault: Boolean
) {

    val notes = viewModel.searchResults.collectAsStateWithLifecycle()

    var query by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background).fillMaxSize(),
    ) {
        TextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.searchNotes(query, inVault)
                            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            placeholder = { Text(text = "Search Notes") },
            leadingIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Search Icon")
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                }
            ),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        if (query == ""){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Search Notes",
                    fontFamily = poppinsFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium)
            }
        } else if (notes.value.notes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Oops! No Notes Found",
                    fontFamily = poppinsFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium)
            }
        } else{
            LazyColumn(
                modifier = Modifier.background(MaterialTheme.colorScheme.background).fillMaxSize()
            ) {
                items(notes.value.notes, key = { note -> note.noteId!! }){ note ->
                    NoteGridCard(
                        note = note,
                        isPinned = note.isPinned,
                        isSelected = false,
                        isInSelectionMode = false,
                        onCardClick = {
                            navController.navigate(
                                AddEditNote(noteId = note.noteId!!)
                            )
                        }
                    ){

                    }
                }
            }
        }
    }
}