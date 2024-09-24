
package com.mohamed.rubynotes.ui.homeScreen

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mohamed.rubynotes.domain.model.Note
import com.mohamed.rubynotes.ui.composables.ConfirmDelete
import com.mohamed.rubynotes.ui.composables.GridAgendaViewButton
import com.mohamed.rubynotes.ui.composables.NotesSelected
import com.mohamed.rubynotes.ui.composables.OrderType
import com.mohamed.rubynotes.ui.composables.SelectAllButton
import com.mohamed.rubynotes.ui.composables.SortOrderButton
import com.mohamed.rubynotes.ui.homeScreen.composables.HomeBottomBarSelected
import com.mohamed.rubynotes.ui.homeScreen.composables.HomeTopBar
import com.mohamed.rubynotes.ui.homeScreen.composables.NoteGridCard
import com.mohamed.rubynotes.ui.homeScreen.composables.ToggleListNotes
import com.mohamed.rubynotes.ui.navigation.AddEditNote
import com.mohamed.rubynotes.ui.navigation.Search
import com.mohamed.rubynotes.ui.navigation.Settings
import com.mohamed.rubynotes.ui.navigation.VaultScreen
import com.mohamed.rubynotes.utils.BiometricPromptManager
import java.util.ArrayList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel ,
    navController: NavController,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    promptManager: BiometricPromptManager
){
    val notes by viewModel.notesScreen.collectAsStateWithLifecycle()

    NotesScreenContent(notes = notes,
        navController,
        viewModel,
        modifier,
        scrollBehavior,
        promptManager
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreenContent(
    notes: HomeScreenState,
    navController: NavController,
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    promptManager: BiometricPromptManager,
    inVault: Boolean = false
){
    val context = LocalContext.current

    var ascending by remember {
        mutableStateOf(false)
    }

    var orderType by remember {
        mutableStateOf("Date Modified")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var showAlertDialog by remember {
        mutableStateOf(false)
    }

    val orderTypes = listOf(
        "Title",
        "Date Created",
        "Date Modified"
    )

    var gridMode by rememberSaveable {
        mutableStateOf(true)
    }

    var isInSelectionMode by remember {
        mutableStateOf(false)
    }

    val selectedNotes = remember {
        mutableStateListOf<Note>()
    }

    val shareableNotes: ArrayList<CharSequence> = arrayListOf()

    selectedNotes.forEach{
        shareableNotes.add(it.body)
    }

    val resetSelectionMode = {
        isInSelectionMode = false
        selectedNotes.clear()
    }

    val biometricResult by promptManager.promptResults.collectAsState(initial = null)

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putCharSequenceArrayListExtra(Intent.EXTRA_TEXT, shareableNotes)
        type = "text/html"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)

    fun selectAllNotes(){
        selectedNotes.clear()
        selectedNotes.addAll(notes.notes)
    }

    BackHandler(
        enabled = isInSelectionMode,
    ) {
        resetSelectionMode()
    }

    LaunchedEffect(biometricResult) {
        if (biometricResult == BiometricPromptManager.BiometricResult.AuthenticationSuccess){
            navController.navigate(VaultScreen)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            if (!isInSelectionMode && !inVault){
                FloatingActionButton(
                    onClick = {
                    navController.navigate(AddEditNote(-1))
                },containerColor = MaterialTheme.colorScheme.primary) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Add New Note Button")
                }
            }
        },

        floatingActionButtonPosition = FabPosition.End,

        topBar = {
            HomeTopBar(
                scrollBehavior = scrollBehavior,
                onSettingsClick = {
                    navController.navigate(Settings)
                },
                onVaultClick = {
                    promptManager.showBiometricPrompt(
                        title = "Go To Vault Screen",
                        description = "Go To Vault Screen",
                        context = context
                    )
                }
            )
        },

        bottomBar = {
            if (isInSelectionMode){
                HomeBottomBarSelected(
                    inVault = inVault,
                    onPinClick = {
                        viewModel.pinNote(selectedNotes){
                            resetSelectionMode()
                        }
                    },
                    onShareClick = {
                        startActivity(context, shareIntent, null)
                    },
                    onLockClick = {
                        viewModel.lockNote(selectedNotes){
                            resetSelectionMode()
                        }
                    }
                ) {
                  showAlertDialog = true
                }
            }
        }

    ) { padding ->
        if (
            showAlertDialog
        ) {
            ConfirmDelete(
                onDismissRequest = { showAlertDialog = false}
            ) {
                viewModel.deleteNote(selectedNotes){
                    resetSelectionMode()
                }
                showAlertDialog = false
            }
        }
        ToggleListNotes(
            padding = padding,
            notes,
            gridMode,
            searchBar = {
                AnimatedVisibility(!isInSelectionMode) {
                    Card (
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        onClick = { navController.navigate(Search(inVault)) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxHeight(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                            Text(
                                text = "Search Notes...",
                                modifier = Modifier.weight(1f),
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            GridAgendaViewButton(isGrid = gridMode) {
                                gridMode = !gridMode
                            }
                        }
                    }
                }
            },
            rowContent = {
                Row(
                    modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    NotesSelected(
                        isInSelectionMode = isInSelectionMode,
                        selectAllButton = {
                            SelectAllButton(
                                selectedItems = selectedNotes,
                                notesSize = notes.notes.size,
                                selectAllItems = ::selectAllNotes)},
                        countNotes = notes.notes.size,
                        countSelectedNotes = selectedNotes.size,
                        modifier = Modifier.weight(1f)
                    )
                    OrderType(
                        expanded,
                        isInSelectionMode,
                        orderType,
                        orderTypes,
                        onClick = { expanded = true },
                        onDismissRequest = { expanded = false }
                    ) {
                        if (!inVault) viewModel.getAllNotes(it.replace(" ", "") + if (ascending) "Asc" else "Desc") else viewModel.getLockedNotes(it.replace(" ", "") + if (ascending) "Asc" else "Desc")
                        orderType = it
                        expanded = false
                    }

                    VerticalDivider(modifier = Modifier
                        .height(20.dp),
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 1.25.dp
                    )

                    SortOrderButton(ascending = ascending, isInSelectionMode) {
                        ascending = !ascending
                        if (!inVault) viewModel.getAllNotes(orderType.replace(" ", "") + if (ascending) "Asc" else "Desc") else viewModel.getLockedNotes(orderType.replace(" ", "") + if (ascending) "Asc" else "Desc")
                    }
                }
            }
        ) { note, modifier ->

            val isSelected = selectedNotes.contains(note)

            NoteGridCard(
                modifier = modifier,
                note = note,
                isPinned =  note.isPinned,
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
                        navController.navigate(
                            AddEditNote(noteId = note.noteId!!)
                        )
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

