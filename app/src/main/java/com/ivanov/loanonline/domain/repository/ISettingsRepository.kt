package com.ivanov.loanonline.domain.repository

import kotlinx.coroutines.flow.Flow

interface ISettingsRepository {
    suspend fun setCredential(token: String)
    fun getCredential(): Flow<String?>
}