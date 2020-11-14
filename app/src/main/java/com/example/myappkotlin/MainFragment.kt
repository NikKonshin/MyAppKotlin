package com.example.myappkotlin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(R.layout.main_fragment) {

    private var getData= GetData()
    lateinit var myViewModel: MyViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        lifecycle.addObserver(getData)

        myViewModel.myLiveData.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        button.setOnClickListener {
            myViewModel.myLiveData.setValueToMyLiveData(editText.text.toString())
        }
    }


}