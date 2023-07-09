package com.ivanov.loanonline.core.presentation.lce

sealed class Lce<out T> {

    object Loading : Lce<Nothing>()

    data class Content<T>(
        val data: T
    ) : Lce<T>()

    data class Error(
        val errorString: String
    ) : Lce<Nothing>()
}