package com.example.myappkotlin.presentation

import androidx.lifecycle.*
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.data.NotesRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val notesRepository: NotesRepository, var note: Note?) : ViewModel() {
    private val showErrorLiveData = MutableLiveData<Boolean>()

    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)

    }

    fun saveNote() {
        viewModelScope.launch {
            val noteValue = note ?: return@launch

            try {
                notesRepository.addOrReplaceNote(noteValue)
            } catch (th: Throwable) {
                showErrorLiveData.value = true
            }
            note?.let {
            }
        }
    }

    fun showError(): LiveData<Boolean> = showErrorLiveData

    private fun generateNote(): Note {
        return Note()
    }

    fun deleteNote() {
        viewModelScope.launch {
            val noteValue = note ?: return@launch

            try {
                notesRepository.deleteNote(noteValue)

            } catch (th: Throwable) {
                showErrorLiveData.value = true
            }
        }
    }

}
