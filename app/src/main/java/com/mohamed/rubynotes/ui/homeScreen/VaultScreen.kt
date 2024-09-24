package com.mohamed.rubynotes.ui.homeScreen

import android.app.Activity
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mohamed.rubynotes.utils.BiometricPromptManager
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaultScreen(viewModel: HomeScreenViewModel,
                navController: NavController,
                modifier: Modifier = Modifier,
                scrollBehavior: TopAppBarScrollBehavior,
                promptManager: BiometricPromptManager
){
    val notes by viewModel.vaultScreen.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    val activity =  LocalContext.current as Activity

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver{_, event ->
            when(event) {
                Lifecycle.Event.ON_START -> {
                    activity.window.setFlags(
                        WindowManager.LayoutParams.FLAG_SECURE,
                        WindowManager.LayoutParams.FLAG_SECURE
                    )
                }
                Lifecycle.Event.ON_PAUSE -> {
                    activity.window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
                }
                else -> {
                    Log.d("Thingy", "on else")
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    NotesScreenContent(notes = notes,
        navController,
        viewModel,
        modifier,
        scrollBehavior,
        promptManager,
        inVault = true
    )
}

