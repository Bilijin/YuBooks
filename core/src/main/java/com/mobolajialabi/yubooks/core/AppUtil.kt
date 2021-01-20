package com.mobolajialabi.yubooks.core

import android.view.View
import com.google.android.material.snackbar.Snackbar

object Helpers{
    fun View.hide() {
        this.visibility = View.GONE
    }

    fun View.reveal() {
        this.visibility = View.VISIBLE
    }

    fun showMessage(message: String, view: View){
        Snackbar.make(view, message,Snackbar.LENGTH_LONG).show()
    }
}