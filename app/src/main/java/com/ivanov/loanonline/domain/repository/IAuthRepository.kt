package com.ivanov.loanonline.domain.repository

import com.ivanov.loanonline.data.model.remote.LoginEntity
import com.ivanov.loanonline.data.model.remote.UserEntity

interface IAuthRepository {
    suspend fun postRegistrationData(registrationData: LoginEntity): Result<UserEntity>
    suspend fun postLoginData(loginData: LoginEntity): Result<String>
}