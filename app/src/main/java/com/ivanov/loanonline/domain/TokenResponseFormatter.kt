package com.ivanov.loanonline.domain

object TokenResponseFormatter {
    operator fun invoke(tokenResponse: String): String? {
        val stringList = tokenResponse.split(" ")
        return if (stringList.size == 2) {
            stringList[1]
        } else {
            null
        }
    }
}