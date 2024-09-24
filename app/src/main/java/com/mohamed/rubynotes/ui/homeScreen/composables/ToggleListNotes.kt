package com.mohamed.rubynotes.ui.homeScreen.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohamed.rubynotes.domain.model.Note
import com.mohamed.rubynotes.ui.homeScreen.HomeScreenState

@Composable
fun ToggleListNotes(
    padding:PaddingValues,
    notes: HomeScreenState,
    isGrid: Boolean,
    searchBar: @Composable () -> Unit,
    rowContent: @Composable () -> Unit,
    notesContent: @Composable (note: Note, modifier: Modifier) -> Unit
){
  if (isGrid){
      LazyVerticalStaggeredGrid(
          modifier = Modifier,
          columns = StaggeredGridCells.Fixed(2),
          contentPadding = PaddingValues(
              start = 4.dp,
              end = 4.dp,
              top = padding.calculateTopPadding(),
              bottom = padding.calculateBottomPadding()),
      ) {
          item(span = StaggeredGridItemSpan.FullLine) {
            searchBar()
          }

          item(span = StaggeredGridItemSpan.FullLine) {
              rowContent()
          }

          items(notes.notes, key = {item: Note -> item.noteId!!}){ note ->
              notesContent(note, Modifier.animateItem())
          }
      }
  } else {
      LazyColumn(
          modifier = Modifier,
          contentPadding = PaddingValues(
              start = 4.dp,
              end = 4.dp,
              top = padding.calculateTopPadding(),
              bottom = padding.calculateBottomPadding()),

      ) {
          item {
              searchBar()
          }

          item {
              rowContent()
          }

          items(notes.notes, key = {item: Note -> item.noteId!!}){ note ->
              notesContent(note,  Modifier.animateItem())
          }
      }
  }
}

