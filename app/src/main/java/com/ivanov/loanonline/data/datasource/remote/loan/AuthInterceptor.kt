package com.ivanov.loanonline.data.datasource.remote.loan

import com.ivanov.loanonline.data.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : Interceptor {

//     Перехватчики OkHttp работают в пуле потоков, а не в основном потоке, поэтому runblocking будет безопасным.
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            settingsRepository.getCredential().first()
        }
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}