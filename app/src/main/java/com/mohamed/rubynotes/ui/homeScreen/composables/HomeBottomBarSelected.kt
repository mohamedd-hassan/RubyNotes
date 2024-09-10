package com.mohamed.rubynotes.ui.homeScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeBottomBarSelected(
    onPinClick: () -> Unit,
    onLockClick: () -> Unit,
    onDeleteClick: () -> Unit
){
    BottomAppBar{
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onPinClick) {
                Icon(imageVector = Icons.Outlined.PushPin, contentDescription = " Pin Notes")
            }
            IconButton(onClick = onLockClick) {
                Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Lock Notes")
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete Notes")
            }
        }
    }
}