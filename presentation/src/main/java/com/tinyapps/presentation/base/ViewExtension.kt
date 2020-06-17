package com.tinyapps.presentation.base

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.hideKeyboard(context: Context) {
    val imm = context.getSystemService (Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}