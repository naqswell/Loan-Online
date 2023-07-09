package com.ivanov.loanonline.data.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ivanov.loanonline.domain.datasource.ICredentialDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class TokenDataSource @Inject constructor(
    appContext: Context
) : ICredentialDataSource {

    private val Context.dataStore by preferencesDataStore(name = CREDENTIAL_DATASTORE_FILE)

    companion object {
        const val CREDENTIAL_DATASTORE_FILE = "credential"
        private val TOKEN = stringPreferencesKey("credentialToken")
    }

    private val tokenDataStore = appContext.dataStore

    override suspend fun set(token: String) {
        tokenDataStore.edit { credential ->
            credential[TOKEN] = token
        }
    }

    override fun get(): Flow<String?> = tokenDataStore.data.map { credential ->
        credential[TOKEN]
    }

}