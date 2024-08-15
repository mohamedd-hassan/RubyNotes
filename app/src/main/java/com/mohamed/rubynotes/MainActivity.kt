package com.mohamed.rubynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.ai.client.generativeai.GenerativeModel
import com.mohamed.rubynotes.ui.addEditNote.NoteScreen
import com.mohamed.rubynotes.ui.theme.RubyNotesTheme
import kotlinx.serialization.Serializable
import com.mohamed.rubynotes.ui.homeScreen.HomeScreen
import com.mohamed.rubynotes.ui.homeScreen.HomeScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
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
                            HomeScreen(viewModel = viewModel, navController)
                        }
                        composable<AddEditNote> {
                            val args = it.toRoute<AddEditNote>()
                            NoteScreen(viewModel, navController, args.noteId)
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object HomeScreen

@Serializable
data class AddEditNote(
    val noteId:Int
)