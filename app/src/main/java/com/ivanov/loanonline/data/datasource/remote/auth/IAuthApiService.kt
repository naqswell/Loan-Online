package com.ivanov.loanonline.data.datasource.remote.auth

import com.ivanov.loanonline.data.model.remote.LoginEntity
import com.ivanov.loanonline.data.model.remote.UserEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthApiService {

    @POST(AuthEndpoints.REGISTRATION)
    suspend fun postRegistrationData(@Body registrationData: LoginEntity): Result<UserEntity>

    @POST(AuthEndpoints.LOGIN)
    suspend fun postLoginData(@Body loginData: LoginEntity): Result<String>
}