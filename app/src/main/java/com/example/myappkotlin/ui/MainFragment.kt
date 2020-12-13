package com.example.myappkotlin.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.example.myappkotlin.presentation.NotesViewModel
import com.example.myappkotlin.R
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.presentation.ViewState
import com.example.myappkotlin.ui.adapter.NotesAdapter
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.toolbar
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel by viewModel<NotesViewModel>()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken("fafas")
            .requestEmail()
            .build()
        googleSignInClient = context?.let { GoogleSignIn.getClient(it,gso) }!!

        val adapter = NotesAdapter {
            navigateToNote(it)
        }

        mainRecycler.adapter = adapter

        viewModel.observeViewState().observe(viewLifecycleOwner) { it ->
            when (it) {
                is ViewState.Value -> {
                    adapter.submitList(it.notes)
                }
                ViewState.EMPTY -> Unit
            }
        }

        fab.setOnClickListener {
            navigateToCreation()
        }
    }

    private fun navigateToNote(note: Note) {
        (requireActivity() as MainActivity).navigateTo(NoteFragment.create(note))
    }

    private fun navigateToCreation() {
        (requireActivity() as MainActivity).navigateTo(NoteFragment.create(null))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_item_notes, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> showLogoutDialog().let { true }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout(){
        context?.let {
            AuthUI.getInstance()
                .signOut(it)
                .addOnCompleteListener {
                    startActivity(Intent(activity, SplashActivity::class.java))
                }
        }
    }

    private fun showLogoutDialog() {
    context?.let {
        AlertDialog.Builder(it)
            .setTitle(R.string.logout_dialog_title)
            .setMessage(R.string.logout_dialog_message)
            .setPositiveButton(R.string.ok_bth_title) { _, _ ->  logout() }
            .setNegativeButton(R.string.logout_dialog_cancel) {dialog, _ -> dialog.dismiss() }
            .show()
    }
    }
}