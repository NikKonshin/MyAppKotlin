package com.example.myappkotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.myappkotlin.data.NotesRepositoryImpl

class NotesViewModel : ViewModel() {

    fun observeViewState(): LiveData<ViewState> = NotesRepositoryImpl.observeNotes()
        .map {
            if (it.isEmpty()) ViewState.EMPTY else ViewState.Value(it)
        }

}