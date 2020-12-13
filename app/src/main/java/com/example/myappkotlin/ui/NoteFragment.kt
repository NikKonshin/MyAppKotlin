package com.example.myappkotlin.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.observe

import com.example.myappkotlin.R
import com.example.myappkotlin.data.Note
import com.example.myappkotlin.databinding.FragmentNoteBinding
import com.example.myappkotlin.presentation.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NoteFragment : Fragment(R.layout.fragment_note) {
    private val note: Note? by lazy(LazyThreadSafetyMode.NONE) { arguments?.getParcelable(NOTE_KEY) }

    private val viewModel by viewModel<NoteViewModel> {
        parametersOf(note)
    }

    private var _binding: FragmentNoteBinding? = null
    private val binding: FragmentNoteBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        with(binding) {
            viewModel.note?.let {
                titleEt.setText(it.title)
                bodyEt.setText(it.note)
            }

            viewModel.showError().observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Error while saving note", Toast.LENGTH_LONG)
                    .show()
            }

            titleEt.addTextChangedListener {
                viewModel.updateTitle(it?.toString() ?: "")
            }
            bodyEt.addTextChangedListener {
                viewModel.updateNote(it?.toString() ?: "")
                viewModel.saveNote()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_item_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.button_back -> {viewModel.saveNote()
                fragmentManager?.popBackStack() }
            R.id.note_delete_button -> deleteNote().let{true}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        viewModel.deleteNote()
        fragmentManager?.popBackStack()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        const val NOTE_KEY: String = "Note"

        fun create(note: Note? = null): NoteFragment {
            val fragment = NoteFragment()
            val arguments = Bundle()
            arguments.putParcelable(NOTE_KEY, note)
            fragment.arguments = arguments

            return fragment
        }
    }

}
