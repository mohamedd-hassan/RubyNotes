package com.mohamed.rubynotes.ui.addEditNote.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FormatButton(
    selected: Boolean,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = Color.Transparent,
    onChangeClick: (Boolean) -> Unit,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick()
                onChangeClick(!selected)
            }
            .border(1.dp,
                color = if (selected) selectedColor else unselectedColor,
                shape = CircleShape)
            .padding(all = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview
@Composable
fun ControlWrapperPreview(){
    var something by remember {
        mutableStateOf(true)
    }
    FormatButton(selected = something, onChangeClick ={something = it}, onClick = {  }) {
        Icon(
            imageVector = Icons.Default.FormatBold,
            contentDescription = "Bold Control",
            tint = if (something) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        )
    }
}