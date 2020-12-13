package com.example.myappkotlin.presentation

import androidx.lifecycle.*
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.data.NotesRepository

class NoteViewModel(private val notesRepository: NotesRepository, var note: Note?) : ViewModel() {

    private val showErrorLiveData = MutableLiveData<Boolean>()
    private val lifecycleOwner: LifecycleOwner = LifecycleOwner { viewModelLifecycle }
    private val viewModelLifecycle = LifecycleRegistry(lifecycleOwner).also {
        it.currentState = Lifecycle.State.RESUMED
    }

    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)

    }

    fun saveNote() {
        note?.let { note: Note ->
            notesRepository.addOrReplaceNote(note)
                .observe(lifecycleOwner) {
                    it.onFailure {
                        showErrorLiveData.value = true
                    }
                }
        }
    }

    fun showError(): LiveData<Boolean> = showErrorLiveData

    override fun onCleared() {
        super.onCleared()
        viewModelLifecycle.currentState = Lifecycle.State.DESTROYED
    }

    private fun generateNote(): Note {
        return Note()
    }

    fun deleteNote() {
        note?.let { note: Note ->
            notesRepository.deleteNote(note)
                .observe(lifecycleOwner) {
                    it.onFailure {
                        showErrorLiveData.value = true
                    }
                    it.onSuccess {

                    }

                }
        }
    }

}
