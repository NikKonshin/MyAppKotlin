package com.example.myappkotlin.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.data.NotesRepository
import com.google.firebase.firestore.*

const val NOTES_COLLECTION = "notes"
const val TAG = "FireStoreDataBase"

class FireStoreDataBaseProvider : DatabaseProvider {
    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)
    private val  result = MutableLiveData<List<Note>>()
    private var subscribeOnDB = false



    override fun observeNotes(): LiveData<List<Note>> {
        if (!subscribeOnDB) subscribeForDBChanging()
        return result
    }

    override fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>> {
        val  result = MutableLiveData<Result<Note>>()
        notesReference
            .document(newNote.id.toString())
            .set(newNote).addOnSuccessListener {
                Log.d(TAG,"Note $newNote is saved")
                result.value = Result.success(newNote)
            }
            .addOnFailureListener {
                Log.d(TAG,"Error saving note $newNote, message: ${it.message}")
                result.value = Result.failure(it)
            }
        return result
    }

    private fun subscribeForDBChanging(){
        notesReference.addSnapshotListener { value, error ->
            if (error != null) {
                Log.e(TAG, "Observe note exception: $error")
            } else if (value != null) {
                val notes = mutableListOf<Note>()


                for (doc: QueryDocumentSnapshot in value) {
                    notes.add(doc.toObject(Note::class.java))
                }
                result.value = notes
            }
        }
        subscribeOnDB = true
    }
}