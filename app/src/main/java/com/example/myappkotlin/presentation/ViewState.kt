package com.example.myappkotlin.presentation

import com.example.myappkotlin.data.Note

sealed class ViewState {
    data class Value(val notes: List<Note>) : ViewState()
    object EMPTY : ViewState()
}