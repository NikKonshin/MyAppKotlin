package com.example.myappkotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.myappkotlin.data.NotesRepository

class NotesViewModel(private val notesRepository: NotesRepository) : ViewModel() {
    fun observeViewState(): LiveData<ViewState> = notesRepository.observeNotes()
        .map {
            if (it.isEmpty()) ViewState.EMPTY else ViewState.Value(it)
        }
}