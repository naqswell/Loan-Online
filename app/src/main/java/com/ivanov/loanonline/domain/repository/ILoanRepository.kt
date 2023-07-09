package com.ivanov.loanonline.domain.repository

import com.ivanov.loanonline.data.model.remote.LoanConditionsEntity
import com.ivanov.loanonline.data.model.remote.LoanEntity
import com.ivanov.loanonline.data.model.remote.LoanRequestEntity

interface ILoanRepository {
    suspend fun getConditions(): Result<LoanConditionsEntity>
    suspend fun createLoan(loanRequest: LoanRequestEntity): Result<LoanEntity>
    suspend fun getAllLoanRequests(): Result<List<LoanEntity>>
}