package com.example.myappkotlin.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.myappkotlin.R

class LogoutDialog(context: Context?) : DialogFragment() {
    companion object {
        val TAG = LogoutDialog::class.java.name + "TAG"
        fun createInstance(context: Context?) = LogoutDialog(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(context)
            .setTitle(R.string.logout_dialog_title)
            .setMessage(R.string.logout_dialog_message)
            .setPositiveButton(R.string.ok_bth_title) { _, _ ->  (activity as LogoutListener).onLogout() }
            .setNegativeButton(R.string.logout_dialog_cancel) {_, _ -> dismiss() }
            .create()

    interface LogoutListener {
        fun onLogout()
    }
}