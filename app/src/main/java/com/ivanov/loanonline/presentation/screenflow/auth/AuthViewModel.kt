package com.ivanov.loanonline.presentation.screenflow.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivanov.loanonline.data.model.remote.LoginEntity
import com.ivanov.loanonline.domain.InputStatus
import com.ivanov.loanonline.domain.InputValidator
import com.ivanov.loanonline.domain.TokenResponseFormatter
import com.ivanov.loanonline.domain.repository.IAuthRepository
import com.ivanov.loanonline.domain.repository.ISettingsRepository
import com.ivanov.loanonline.presentation.common.IMainRouter
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val router: IMainRouter,
    private val settingsRepository: ISettingsRepository,
    private val loginRepository: IAuthRepository
) : ViewModel() {

    private val <T> LiveData<T>.mutable: MutableLiveData<T>
        get() = this as MutableLiveData<T>

    val nameInputStatus: LiveData<InputStatus> = MutableLiveData()
    val passwordInputStatus: LiveData<InputStatus> = MutableLiveData()
    val isLoading: LiveData<Boolean> = MutableLiveData(false)

    fun updateNameInput(name: String) {
        val nameStatus = InputValidator.validateName(name)
        nameInputStatus.mutable.value = nameStatus
    }

    fun updatePasswordInput(name: String) {
        val passwordStatus = InputValidator.validatePassword(name)
        passwordInputStatus.mutable.value = passwordStatus
    }

    fun sendLoginData() {
        val nameStatus = nameInputStatus.value
        val passwordStatus = passwordInputStatus.value
        if (nameStatus is InputStatus.Correct && passwordStatus is InputStatus.Correct) {
            val loginEntity = LoginEntity(
                name = nameStatus.input,
                password = passwordStatus.input
            )

            viewModelScope.launch {
                isLoading.mutable.value = true
                loginRepository.postRegistrationData(loginEntity)
                loginRepository.postLoginData(loginEntity)
                    .onSuccess { token ->
                        val formattedToken = TokenResponseFormatter(token)
                        formattedToken?.let {
                            settingsRepository.setCredential(formattedToken)
                            router.openLoanScreen()
                        }
                    }
                    .onFailure {
                    }
                isLoading.mutable.value = false
            }
        }
    }
}