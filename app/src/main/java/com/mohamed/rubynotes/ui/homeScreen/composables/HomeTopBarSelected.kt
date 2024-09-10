package com.mohamed.rubynotes.ui.homeScreen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.RadioButtonUnchecked
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohamed.rubynotes.data.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBarSelected(
    scrollBehavior: TopAppBarScrollBehavior,
    selectedItems: SnapshotStateList<Note>,
    notesSize: Int,
    selectAllItems: () -> Unit
    ){
    TopAppBar(
        title = { Text(text = "${selectedItems.size} Selected") },
        navigationIcon = {
            Column {
                IconButton(
                    onClick = {
                        if (selectedItems.size == notesSize){
                            selectedItems.clear()
                        } else
                        {
                            selectAllItems()
                        }
                    }
                ) {
                    if (selectedItems.size == notesSize) {
                        Column {
                            Icon(
                                imageVector = Icons.Rounded.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(text = "All")
                        }
                    } else {
                        Column {
                            Icon(
                                imageVector = Icons.Rounded.RadioButtonUnchecked,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(text = "All")
                        }
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

