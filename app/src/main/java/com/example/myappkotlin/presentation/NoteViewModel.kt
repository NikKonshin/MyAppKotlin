package com.example.myappkotlin.presentation

import androidx.lifecycle.*
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.data.notesRepository

class NoteViewModel(var note: Note?) : ViewModel(), LifecycleOwner {

    private val showErrorLiveData = MutableLiveData<Boolean>()
    val lifecycle = LifecycleRegistry(this).also {
        it.currentState = Lifecycle.State.RESUMED
    }
    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)

    }

    fun saveNote(){
        note?.let {note : Note ->
            notesRepository.addOrReplaceNote(note)
                .observe(this){
                it.onFailure {
                    showErrorLiveData.value = true
                }
            }
        }
    }

    fun showError(): LiveData<Boolean> = showErrorLiveData

    override fun onCleared() {
        super.onCleared()
        lifecycle.currentState = Lifecycle.State.DESTROYED
    }

    private fun generateNote(): Note {
        return Note()
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycle
    }
}