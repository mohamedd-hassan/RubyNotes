package com.mohamed.rubynotes.ui.addEditNote.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatAlignLeft
import androidx.compose.material.icons.automirrored.filled.FormatAlignRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatColorText
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState

@Composable
fun NoteBottomRow(modifier: Modifier = Modifier,
                  state: RichTextState,
                  titleSize: TextUnit = MaterialTheme.typography.displaySmall.fontSize,
                  subtitleSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize,
                  onBoldClick: () -> Unit = {state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))},
                  onItalicClick: () -> Unit = {state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))},
                  onUnderlineClick: () -> Unit = {state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))},
                  onTitleClick: () -> Unit = {state.toggleSpanStyle(SpanStyle(fontSize = titleSize))},
                  onSubtitleClick: () -> Unit = {state.toggleSpanStyle(SpanStyle(fontSize = subtitleSize))},
                  onTextColorClick: () -> Unit = {state.toggleSpanStyle(SpanStyle(color = Color.Red))},
                  onStartAlignClick: () -> Unit = {state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Start))},
                  onEndAlignClick: () -> Unit = {state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.End))},
                  onCenterAlignClick: () -> Unit = {state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))},
                  onToggleOrderedList: () -> Unit = {state.toggleOrderedList()}
) {
    val currentSpanStyle = state.currentSpanStyle
    val currentParagraphStyle = state.currentParagraphStyle
    var boldSelected by rememberSaveable { mutableStateOf(false) }
    var italicSelected by rememberSaveable { mutableStateOf(false) }
    var underlineSelected by rememberSaveable { mutableStateOf(false) }
    var titleSelected by rememberSaveable { mutableStateOf(false) }
    var subtitleSelected by rememberSaveable { mutableStateOf(false) }
    var textColorSelected by rememberSaveable { mutableStateOf(false) }
    var alignmentSelected by rememberSaveable { mutableIntStateOf(0) }
    var toggleOrderedList by rememberSaveable { mutableStateOf(false) }

    boldSelected = currentSpanStyle.fontWeight == FontWeight.Bold
    italicSelected = currentSpanStyle.fontStyle == FontStyle.Italic
    underlineSelected = currentSpanStyle.textDecoration == TextDecoration.Underline
    titleSelected = currentSpanStyle.fontSize == titleSize
    subtitleSelected = currentSpanStyle.fontSize == subtitleSize
    textColorSelected = currentSpanStyle.color == Color.Red
    toggleOrderedList = state.isOrderedList

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp)
            .padding(top = 4.dp, bottom = 4.dp)
            .background(Color(0xFFEBEBEB)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        FormatButton(
            selected = boldSelected,
            onChangeClick = { boldSelected = it },
            onClick = onBoldClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatBold,
                contentDescription = "Bold Control",
                tint = if (boldSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
        FormatButton(
            selected = italicSelected,
            onChangeClick = { italicSelected = it },
            onClick = onItalicClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatItalic,
                contentDescription = "Italic Control",
                tint = if (italicSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
        FormatButton(
            selected = underlineSelected,
            onChangeClick = { underlineSelected = it },
            onClick = onUnderlineClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatUnderlined,
                contentDescription = "Underline Control",
                tint = if (underlineSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
        FormatButton(
            selected = titleSelected,
            onChangeClick = { titleSelected = it },
            onClick = onTitleClick
        ) {
            Icon(
                imageVector = Icons.Default.Title,
                contentDescription = "Title Control",
                tint = if (titleSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
        FormatButton(
            selected = subtitleSelected,
            onChangeClick = { subtitleSelected = it },
            onClick = onSubtitleClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatSize,
                contentDescription = "Subtitle Control",
                tint = if (subtitleSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
        FormatButton(
            selected = textColorSelected,
            onChangeClick = { textColorSelected = it },
            onClick = onTextColorClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatColorText,
                contentDescription = "Text Color Control",
                tint = if (textColorSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
        FormatButton(
            selected = alignmentSelected == 0,
            onChangeClick = { alignmentSelected = 0 },
            onClick = onStartAlignClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.FormatAlignLeft,
                contentDescription = "Start Align Control",
                tint = if (alignmentSelected == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
        FormatButton(
            selected = alignmentSelected == 1,
            onChangeClick = { alignmentSelected = 1 },
            onClick = onCenterAlignClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatAlignCenter,
                contentDescription = "Center Align Control",
                tint = if (alignmentSelected == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
        FormatButton(
            selected = alignmentSelected == 2,
            onChangeClick = { alignmentSelected = 2 },
            onClick = onEndAlignClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.FormatAlignRight,
                contentDescription = "End Align Control",
                tint = if (alignmentSelected == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
        FormatButton(
            selected = toggleOrderedList,
            onChangeClick = { toggleOrderedList = it},
            onClick = onToggleOrderedList) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = "Order List Button",
                tint = if (toggleOrderedList) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
    }
}

//@Preview
//@Composable
//fun NoteBottomRowPreview(){
//    val state = rememberRichTextState()
//    NoteBottomRow(state = state)
//}
