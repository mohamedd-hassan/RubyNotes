package com.mohamed.rubynotes.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Sort
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SortOrderButton(
    ascending: Boolean,
    onClick: () -> Unit
){
    IconButton(onClick = onClick) {
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

