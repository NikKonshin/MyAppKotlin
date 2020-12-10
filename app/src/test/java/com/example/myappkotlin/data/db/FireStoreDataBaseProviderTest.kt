package com.example.myappkotlin.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myappkotlin.data.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule

class FireStoreDataBaseProviderTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockDb = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()
    private val mockCollection = mockk<CollectionReference>()
    private val mockUser = mockk<FirebaseUser>()
    private val mockDocument1 = mockk<DocumentSnapshot>()
    private val mockDocument2 = mockk<DocumentSnapshot>()
    private val mockDocument3 = mockk<DocumentSnapshot>()
    private val testNotes = listOf(Note(id = 1), Note(id = 2), Note(id = 3))

    private val provider: FireStoreDataBaseProvider = FireStoreDataBaseProvider(mockAuth, mockDb)
@Before
fun setUp() {

    }
@After
    fun tearDown() {}
}