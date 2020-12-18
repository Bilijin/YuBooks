package com.mobolajialabi.yubooks.util

import android.view.View

object Helpers{
    fun View.hide() {
        this.visibility = View.GONE
    }

    fun View.reveal() {
        this.visibility = View.VISIBLE
    }
}