package com.ivanov.loanonline.domain.datasource

import kotlinx.coroutines.flow.Flow

interface ICredentialDataSource {
    suspend fun set(token: String)
    fun get(): Flow<String?>
}