package com.example.myappkotlin.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.myappkotlin.R
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.presentation.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : Fragment(R.layout.fragment_note) {
    private val note: Note? by lazy(LazyThreadSafetyMode.NONE) { arguments?.getParcelable(NOTE_KEY) }

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NoteViewModel(note) as T
            }
        }).get(NoteViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        viewModel.note?.let {
            titleEt.setText(it.title)
            bodyEt.setText(it.note)
        }

        viewModel.showError().observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "Error while saving note", Toast.LENGTH_LONG).show()
        }

        titleEt.addTextChangedListener {
            viewModel.updateTitle(it?.toString() ?: "")
        }
        bodyEt.addTextChangedListener {
            viewModel.updateNote(it?.toString() ?: "")
            viewModel.saveNote()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_item_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.button_back -> {viewModel.saveNote()
                (requireActivity() as MainActivity).openFragment(MainFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val NOTE_KEY = "Note"

        fun create(note: Note? = null): NoteFragment {
            val fragment = NoteFragment()
            val arguments = Bundle()
            arguments.putParcelable(NOTE_KEY, note)
            fragment.arguments = arguments

            return fragment
        }
    }

}
