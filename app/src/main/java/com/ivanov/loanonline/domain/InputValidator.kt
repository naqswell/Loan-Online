package com.ivanov.loanonline.domain

sealed class InputStatus(val input: String) {
    class ShortLength(data: String) : InputStatus(data)
    class Correct(data: String) : InputStatus(data)
    class NoLetter(data: String) : InputStatus(data)
}

object InputValidator {
    fun validateName(login: String): InputStatus {
        if (login.isNotEmpty()) {
            for (char in login) {
                if (char.isLetter()) {
                    return InputStatus.Correct(login)
                }
            }
            return InputStatus.NoLetter(login)
        } else {
            return InputStatus.ShortLength(login)
        }
    }

    fun validatePassword(password: String): InputStatus {
        if (password.isNotEmpty()) {
            for (char in password) {
                if (char.isLetter()) {
                    return InputStatus.Correct(password)
                }
            }
            return InputStatus.NoLetter(password)
        } else {
            return InputStatus.ShortLength(password)
        }
    }

    fun validatePhone(phone: String): InputStatus {
        return if (phone.isNotEmpty() && (phone.length > 6)) {
            InputStatus.Correct(phone)
        } else {
            InputStatus.ShortLength(phone)
        }
    }
}