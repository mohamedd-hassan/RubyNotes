package com.mohamed.rubynotes

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mohamed.rubynotes.ui.addEditNote.AddEditNoteViewModel
import com.mohamed.rubynotes.ui.addEditNote.NoteScreen
import com.mohamed.rubynotes.ui.homeScreen.HomeScreen
import com.mohamed.rubynotes.ui.homeScreen.HomeScreenViewModel
import com.mohamed.rubynotes.ui.navigation.AddEditNote
import com.mohamed.rubynotes.ui.navigation.HomeScreen
import com.mohamed.rubynotes.ui.theme.RubyNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeScreenViewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
        val addEditNoteViewModel = ViewModelProvider(this)[AddEditNoteViewModel::class.java]
        installSplashScreen()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setContent {
            RubyNotesTheme {
                // A surface container using the 'background' color from the theme
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController,
                        startDestination = HomeScreen) {
                        composable<HomeScreen> {
                            HomeScreen(viewModel = homeScreenViewModel, navController = navController, scrollBehavior = scrollBehavior)
                        }
                        composable<AddEditNote> {
                            val args = it.toRoute<AddEditNote>()
                            NoteScreen(addEditNoteViewModel, args.noteId)
                        }
                    }
                }
            }
        }
    }
}

