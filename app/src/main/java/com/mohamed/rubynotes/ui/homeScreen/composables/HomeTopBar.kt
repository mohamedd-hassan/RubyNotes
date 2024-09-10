package com.mohamed.rubynotes.ui.homeScreen.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    isGrid: Boolean,
    onSettingsClick: () -> Unit,
    onChangeViewClick: () -> Unit
){
    TopAppBar(
        title = { Text(text = "Ruby Notes") },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings")
            }
            IconButton(onClick = onChangeViewClick) {
                Icon(
                    if (isGrid){
                     Icons.Outlined.GridView
                    } else {
                        Icons.Outlined.ViewAgenda
                    },
                    contentDescription = ""
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}