package com.example.myappkotlin.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.errors.NoAuthException
import com.example.myappkotlin.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*

const val NOTES_COLLECTION = "notes"
const val TAG = "FireStoreDataBase"
private const val USERS_COLLECTION = "users"

class FireStoreDataBaseProvider : DatabaseProvider {
    private val db = FirebaseFirestore.getInstance()
    private val result = MutableLiveData<List<Note>>()

    private val currentUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    private var subscribeOnDB = false

    override fun observeNotes(): LiveData<List<Note>> {
        if (!subscribeOnDB) subscribeForDBChanging()
        return result
    }

    private fun getUsersNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override fun deleteNote(note: Note): LiveData<Result<Unit>> {
        val result = MutableLiveData<Result<Unit>>()
        handleNotesReference(
            getUsersNotesCollection()
                .document(note.id.toString())
                .delete()
                .addOnSuccessListener {
                    result.value = Result.success(Unit)
                }
                .addOnFailureListener {
                    result.value = Result.failure(it)
                }
        ) {
            result.value = Result.failure(it)
        }
        return result
    }

    private fun handleNotesReference(
        referenceHandler: Task<Void>,
        exceptionHandler: (Throwable) -> Unit
    ) {

    }

    override fun getCurrentUser() = currentUser?.run { User(displayName, email) }


    override fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>> {
        val result = MutableLiveData<Result<Note>>()
        handleNotesReference(
            {
                getUsersNotesCollection()
                    .document(newNote.id.toString())
                    .set(newNote)
                    .addOnSuccessListener {
                        Log.d(TAG, "Note $newNote is saved")
                        result.value = Result.success(newNote)
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "Error saving note $newNote, message: ${it.message}")
                        result.value = Result.failure(it)
                    }
            }, {
                Log.e(TAG, "Error getting reference note $newNote, message: ${it.message}")
                result.value = Result.failure(it)
            }

        )
        return result
    }

    private fun subscribeForDBChanging() {
        handleNotesReference(
            {
                getUsersNotesCollection().addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.e(TAG, "Observe note exception:$e")
                    } else if (snapshot != null) {
                        val notes = mutableListOf<Note>()

                        for (doc: QueryDocumentSnapshot in snapshot) {
                            notes.add(doc.toObject(Note::class.java))
                        }
                        result.value = notes
                    }
                }
                subscribeOnDB = true
            }, {
                Log.e(TAG, "Error getting reference while subscribed for notes")

            })
    }

    private inline fun handleNotesReference(
        referenceHandler: (CollectionReference) -> Unit,
        exceptionHandler: (Throwable) -> Unit = {}
    ) {
        kotlin.runCatching {
            getUsersNotesCollection()
        }
            .fold(referenceHandler, exceptionHandler)
    }
}