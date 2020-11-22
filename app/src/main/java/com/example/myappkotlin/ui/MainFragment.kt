package com.example.myappkotlin.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myappkotlin.presentation.MyViewModel
import com.example.myappkotlin.R
import com.example.myappkotlin.presentation.ViewState
import com.example.myappkotlin.ui.adapter.NoteAdapter
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(
            MyViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteAdapter()

        mainRecycler.adapter = adapter

        viewModel.observeViewState().observe(viewLifecycleOwner) { it ->
            when (it) {
                is ViewState.Value -> {
                    adapter.notes = it.notes
                }
                ViewState.EMPTY -> Unit
            }
        }

    }


}