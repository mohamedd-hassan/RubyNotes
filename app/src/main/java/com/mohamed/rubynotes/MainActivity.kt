package com.mohamed.rubynotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
import com.mohamed.rubynotes.ui.homeScreen.VaultScreen
import com.mohamed.rubynotes.ui.navigation.AddEditNote
import com.mohamed.rubynotes.ui.navigation.HomeScreen
import com.mohamed.rubynotes.ui.navigation.Search
import com.mohamed.rubynotes.ui.navigation.Settings
import com.mohamed.rubynotes.ui.navigation.VaultScreen
import com.mohamed.rubynotes.ui.search.SearchScreen
import com.mohamed.rubynotes.ui.settingsScreen.SettingsScreen
import com.mohamed.rubynotes.ui.theme.RubyNotesTheme
import com.mohamed.rubynotes.utils.BiometricPromptManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val promptManager by lazy {
        BiometricPromptManager(
            this)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeScreenViewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
        val addEditNoteViewModel = ViewModelProvider(this)[AddEditNoteViewModel::class.java]
        installSplashScreen()
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.auto(lightScrim = Color.White.toArgb(), darkScrim = Color(0xFF242425).toArgb()))
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            RubyNotesTheme {
                // A surface container using the 'background' color from the theme
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Box(
                        modifier = Modifier
                    ) {
                        NavHost(navController = navController,
                            startDestination = HomeScreen) {
                            composable<HomeScreen> {
                                HomeScreen(viewModel = homeScreenViewModel, navController = navController, scrollBehavior = scrollBehavior, promptManager = promptManager)
                            }
                            composable<AddEditNote> {
                                val args = it.toRoute<AddEditNote>()
                                NoteScreen(addEditNoteViewModel, args.noteId)
                            }
                            composable<VaultScreen>{
                                VaultScreen(viewModel = homeScreenViewModel, navController = navController, scrollBehavior = scrollBehavior, promptManager = promptManager)
                            }
                            composable<Settings>{
                                SettingsScreen()
                            }
                            composable<Search>{
                                val args = it.toRoute<Search>()
                                SearchScreen(navController, homeScreenViewModel, args.inVault)
                            }
                        }
                    }
                }
            }
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                promptManager.resultChannel.trySend(BiometricPromptManager.BiometricResult.AuthenticationSuccess)
            }
        }
    }
}

