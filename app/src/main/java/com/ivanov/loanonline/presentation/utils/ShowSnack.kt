package com.ivanov.loanonline.presentation.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnack(string: String) {
    Snackbar.make(
        this,
        string,
        Snackbar.LENGTH_LONG
    ).apply {
        setAction("OK") {}
    }.show()
}