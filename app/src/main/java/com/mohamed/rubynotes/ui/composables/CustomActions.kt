package com.mohamed.rubynotes.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Sort
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.RadioButtonUnchecked
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamed.rubynotes.domain.model.Note
import com.mohamed.rubynotes.ui.theme.poppinsFamily

@Composable
fun SortOrderButton(
    ascending: Boolean,
    isInSelectionMode: Boolean,
    onClick: () -> Unit
){
    IconButton(onClick = onClick, enabled = !isInSelectionMode) {
        Icon(
            imageVector = if (ascending){
                Icons.Filled.ArrowUpward
            } else{
                Icons.Filled.ArrowDownward
            },
            contentDescription = "Sort Icon",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun SettingsButton(
    onClick: () -> Unit
){
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Outlined.Settings,
            tint = Color.Black,
            contentDescription = "Settings")
    }
}

@Composable
fun VaultButton(onClick: () -> Unit){
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Outlined.Lock,
            tint = Color.Black,
            contentDescription = "Vault Screen"
        )
    }
}

@Composable
fun GridAgendaViewButton(
    isGrid: Boolean,
    onClick: () -> Unit
){
    IconButton(onClick = onClick) {
        Icon(
            if (!isGrid){
                Icons.Outlined.ViewAgenda
            } else {
                Icons.Outlined.GridView
            },
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun OrderType(
    expanded: Boolean,
    isInSelectionMode: Boolean,
    orderType: String,
    orderTypes: List<String>,
    onClick: () -> Unit,
    onDismissRequest: () -> Unit,
    onMenuItemClick: (it: String) -> Unit,
){
    Box {
        TextButton(
            onClick = onClick,
            enabled = !isInSelectionMode) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Sort,
                contentDescription = "Sort Icon"
            )
            Text(text = orderType,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        DropdownMenu(
            expanded = expanded, onDismissRequest = onDismissRequest) {
            orderTypes.forEach {
                DropdownMenuItem(
                    text = { Text(text = it)},
                    onClick = { onMenuItemClick(it) }
                )
            }
        }
    }
}

@Composable
fun PinNotesButton(onPinClick: () -> Unit){
    Column(
        modifier = Modifier.fillMaxHeight().padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onPinClick) {
            Icon(
                imageVector = Icons.Outlined.PushPin,
                contentDescription = " Pin Notes",
                tint = MaterialTheme.colorScheme.secondary)
        }
        Text(
            text = "Pin",
            style = MaterialTheme.typography.bodySmall
            )
    }
}

@Composable
fun LockNotesButton(
    inVault: Boolean,
    onLockClick: () -> Unit){
    Column(
        modifier = Modifier.fillMaxHeight().padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onLockClick) {
            Icon(
                imageVector = if (inVault) Icons.Outlined.LockOpen else Icons.Outlined.Lock,
                contentDescription = "Lock Notes",
                tint = MaterialTheme.colorScheme.secondary)
        }
        Text(text = if (inVault) "Unlock" else "Lock",
            style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun ShareNotesButton(onShareClick: () -> Unit){
    Column(
        modifier = Modifier.fillMaxHeight().padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onShareClick) {
            Icon(imageVector = Icons.Outlined.Share,
                contentDescription = "Share Notes",
                tint = MaterialTheme.colorScheme.secondary)
        }
        Text(text = "Share",
            style = MaterialTheme.typography.bodySmall)

    }
}

@Composable
fun DeleteNotesButton(onDeleteClick: () -> Unit){
    Column(
        modifier = Modifier.fillMaxHeight().padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onDeleteClick) {
            Icon(imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete Notes",
                tint = MaterialTheme.colorScheme.secondary)
        }
        Text(text = "Delete",
            style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun NotesSelected(
    isInSelectionMode: Boolean,
    selectAllButton: @Composable () -> Unit,
    countNotes: Int,
    countSelectedNotes: Int,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.width(IntrinsicSize.Max).padding(start = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isInSelectionMode){
            selectAllButton()
            Text(
                text = "Selected",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                modifier = Modifier.padding( end = 8.dp))
            if (
                countSelectedNotes<100
            ){
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .border(0.dp, Color.Transparent, CircleShape)
                        .size(24.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {

                    Text(text = countSelectedNotes.toString(),
                        color = Color.Black,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else{
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .border(0.dp, Color.Transparent, CircleShape)
                        .size(26.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {

                    Text(text = "99+",
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        } else{
            Text(
                text = "Notes",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp))
            if (
                countNotes<100
            ){
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .border(0.dp, Color.Transparent, CircleShape)
                        .size(24.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {

                    Text(text = countNotes.toString(),
                        color = Color.Black,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else{
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .border(0.dp, Color.Transparent, CircleShape)
                        .size(26.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {

                    Text(text = "99+",
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun SelectAllButton(
    selectedItems: SnapshotStateList<Note>,
    notesSize: Int,
    selectAllItems: () -> Unit
    ){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        IconButton(
            onClick = {
                if (selectedItems.size == notesSize) {
                    selectedItems.clear()
                } else {
                    selectAllItems()
                }
            }
        ) {
            if (selectedItems.size == notesSize) {
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    //modifier = Modifier.padding(8.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    //modifier = Modifier.padding(8.dp)
                )
            }
        }
        Text(text = "All",
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp))
    }
}

@Composable
fun ConfirmDelete(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(text = "Delete Notes")
        },
        text = {
            Text(text = "Are you sure you want to delete these items?")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}