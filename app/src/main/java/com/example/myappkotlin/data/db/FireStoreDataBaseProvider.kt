package com.example.myappkotlin.data.db

import android.util.Log
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.errors.NoAuthException
import com.example.myappkotlin.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

const val NOTES_COLLECTION = "notes"
const val TAG = "FireStoreDataBase"
private const val USERS_COLLECTION = "users"

class FireStoreDataBaseProvider : DatabaseProvider {
    private val db = FirebaseFirestore.getInstance()
    private val result = MutableStateFlow<List<Note>?>(null)
    private val currentUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    private var subscribeOnDB = false

    override fun observeNotes(): Flow<List<Note>> {
        if (!subscribeOnDB) subscribeForDBChanging()
        return result.filterNotNull()
    }

    private fun getUsersNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override suspend fun deleteNote(note: Note) {
        suspendCoroutine<Unit> { continuation ->
            getUsersNotesCollection()
                .document(note.id.toString())
                .delete()
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(Unit))
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    override fun getCurrentUser() = currentUser?.run { User(displayName, email) }


    override suspend fun addOrReplaceNote(newNote: Note) {
        suspendCoroutine<Note> { continuation ->
            handleNotesReference(
                {
                    getUsersNotesCollection()
                        .document(newNote.id.toString())
                        .set(newNote)
                        .addOnSuccessListener {
                            Log.d(TAG, "Note $newNote is saved")
                            continuation.resumeWith(Result.success(newNote))
                        }
                        .addOnFailureListener {
                            Log.e(TAG, "Error saving note $newNote, message: ${it.message}")
                            continuation.resumeWithException(it)
                        }
                }, {
                    Log.e(TAG, "Error getting reference note $newNote, message: ${it.message}")
                    continuation.resumeWithException(it)
                }
            )
        }
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