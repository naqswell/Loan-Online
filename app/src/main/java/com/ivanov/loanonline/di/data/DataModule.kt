package com.ivanov.loanonline.di.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ivanov.loanonline.core.network.ResultCallAdapterFactory
import com.ivanov.loanonline.data.datasource.local.TokenDataSource
import com.ivanov.loanonline.data.datasource.remote.auth.AuthEndpoints
import com.ivanov.loanonline.data.datasource.remote.loan.AuthInterceptor
import com.ivanov.loanonline.data.datasource.remote.auth.IAuthApiService
import com.ivanov.loanonline.data.datasource.remote.loan.ILoanApiService
import com.ivanov.loanonline.data.datasource.remote.loan.LoanEndpoints
import com.ivanov.loanonline.data.repository.AuthRepository
import com.ivanov.loanonline.data.repository.LoanRepository
import com.ivanov.loanonline.data.repository.SettingsRepository
import com.ivanov.loanonline.di.Auth
import com.ivanov.loanonline.di.Loan
import com.ivanov.loanonline.di.app.AppScope
import com.ivanov.loanonline.domain.datasource.ICredentialDataSource
import com.ivanov.loanonline.domain.repository.IAuthRepository
import com.ivanov.loanonline.domain.repository.ILoanRepository
import com.ivanov.loanonline.domain.repository.ISettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
interface DataModule {

    companion object {

        @Provides
        @AppScope
        fun loggingInterceptor() = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        @Provides
        @AppScope
        @Auth
        fun authOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }

        @Provides
        @AppScope
        @Loan
        fun loanOkHttpClient(
            authInterceptor: AuthInterceptor,
            loggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        }

        @Provides
        @AppScope
        fun gson(): Gson = GsonBuilder()
            .setLenient()
            .create()


        @Provides
        @AppScope
        @Auth
        fun authRetrofit(
            @Auth okHttpClient: OkHttpClient,
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(AuthEndpoints.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .addCallAdapterFactory(ResultCallAdapterFactory())
                .client(okHttpClient)
                .build()
        }

        @Provides
        @AppScope
        @Loan
        fun loanRetrofit(
            @Loan okHttpClient: OkHttpClient,
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(LoanEndpoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .addCallAdapterFactory(ResultCallAdapterFactory())
                .client(okHttpClient)
                .build()
        }

        @Provides
        @AppScope
        fun coroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        @AppScope
        fun authInterceptor(tokenManager: SettingsRepository): AuthInterceptor =
            AuthInterceptor(tokenManager)

        @Provides
        @AppScope
        fun authApiService(@Auth retrofit: Retrofit): IAuthApiService =
            retrofit.create(IAuthApiService::class.java)

        @Provides
        @AppScope
        fun loanApiService(@Loan retrofit: Retrofit): ILoanApiService =
            retrofit.create(ILoanApiService::class.java)
    }

    @Binds
    @AppScope
    fun ceredentialDataSource(tokenDataSource: TokenDataSource): ICredentialDataSource

    @Binds
    @AppScope
    fun settingsRepositiry(settingsRepository: SettingsRepository): ISettingsRepository

    @Binds
    @AppScope
    fun authRepository(authRepository: AuthRepository): IAuthRepository

    @Binds
    @AppScope
    fun loanRepository(loanRepository: LoanRepository): ILoanRepository
}