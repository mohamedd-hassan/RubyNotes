package com.mohamed.rubynotes

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mohamed.rubynotes.ui.addEditNote.AddEditNoteViewModel
import com.mohamed.rubynotes.ui.addEditNote.NoteScreen
import com.mohamed.rubynotes.ui.theme.RubyNotesTheme
import com.mohamed.rubynotes.ui.homeScreen.HomeScreen
import com.mohamed.rubynotes.ui.homeScreen.HomeScreenViewModel
import com.mohamed.rubynotes.ui.navigation.AddEditNote
import com.mohamed.rubynotes.ui.navigation.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeScreenViewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
        val addEditNoteViewModel = ViewModelProvider(this)[AddEditNoteViewModel::class.java]
        installSplashScreen()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {
            RubyNotesTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController,
                        startDestination = HomeScreen) {
                        composable<HomeScreen> {
                            HomeScreen(viewModel = homeScreenViewModel, navController)
                        }
                        composable<AddEditNote> {
                            val args = it.toRoute<AddEditNote>()
                            NoteScreen(addEditNoteViewModel, navController, args.noteId)
                        }
                    }
                }
            }
        }
    }
}

