package com.ivanov.loanonline.data.repository

import com.ivanov.loanonline.data.datasource.remote.auth.IAuthApiService
import com.ivanov.loanonline.data.model.remote.LoginEntity
import com.ivanov.loanonline.data.model.remote.UserEntity
import com.ivanov.loanonline.domain.repository.IAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: IAuthApiService,
    private val dispatcher: CoroutineDispatcher,
) : IAuthRepository {
    override suspend fun postRegistrationData(registrationData: LoginEntity): Result<UserEntity> =
        withContext(dispatcher) {
            authApiService.postRegistrationData(registrationData)
        }

    override suspend fun postLoginData(loginData: LoginEntity): Result<String> = withContext(dispatcher) {
        authApiService.postLoginData(loginData)
    }
}