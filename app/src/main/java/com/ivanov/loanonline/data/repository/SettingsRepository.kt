package com.ivanov.loanonline.data.repository

import com.ivanov.loanonline.domain.datasource.ICredentialDataSource
import com.ivanov.loanonline.domain.repository.ISettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val credentialDataSource: ICredentialDataSource,
    private val dispatcher: CoroutineDispatcher,
) : ISettingsRepository {

    override suspend fun setCredential(token: String) = withContext(dispatcher) {
        credentialDataSource.set(token)
    }

    override fun getCredential(): Flow<String?> = credentialDataSource.get()
}