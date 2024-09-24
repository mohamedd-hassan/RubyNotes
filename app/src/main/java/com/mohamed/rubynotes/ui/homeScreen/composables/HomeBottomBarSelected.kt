package com.mohamed.rubynotes.ui.homeScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohamed.rubynotes.ui.composables.DeleteNotesButton
import com.mohamed.rubynotes.ui.composables.LockNotesButton
import com.mohamed.rubynotes.ui.composables.PinNotesButton
import com.mohamed.rubynotes.ui.composables.ShareNotesButton

@Composable
fun HomeBottomBarSelected(
    inVault: Boolean,
    onPinClick: () -> Unit,
    onLockClick: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
){
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PinNotesButton(onPinClick)
            LockNotesButton(inVault, onLockClick)
            ShareNotesButton(onShareClick)
            DeleteNotesButton(onDeleteClick)
        }
    }
}

@Preview
@Composable
fun HomeBottomBarSelectedPreview(){
    HomeBottomBarSelected(
        inVault = false,
        onPinClick = {},
        onLockClick = {},
        onShareClick = {},
        onDeleteClick = {})
}