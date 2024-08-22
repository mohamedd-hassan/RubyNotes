package com.mohamed.rubynotes.ui.drawNote

import androidx.lifecycle.ViewModel

class StylusViewModel : ViewModel() {

    private fun cancelLastStroke() {
    }
}

inline fun <T> List<T>.findLastIndex(predicate: (T) -> Boolean): Int {
    val iterator = this.listIterator(size)
    var count = 1
    while (iterator.hasPrevious()) {
        val element = iterator.previous()
        if (predicate(element)) {
            return size - count
        }
        count++
    }
    return -1
}