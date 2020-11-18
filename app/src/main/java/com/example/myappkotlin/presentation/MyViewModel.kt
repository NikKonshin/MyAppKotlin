package com.example.myappkotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myappkotlin.data.NotesRepositoryImpl

class MyViewModel : ViewModel() {

    private val mLiveData = MutableLiveData<ViewState>(ViewState.EMPTY)

    init {
        val notes = NotesRepositoryImpl.getNotes()
        mLiveData.value = ViewState.Value(notes)
    }

    fun observeViewState(): LiveData<ViewState> {
        return mLiveData
    }

}