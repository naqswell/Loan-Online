package com.ivanov.loanonline.data.repository

import com.ivanov.loanonline.data.datasource.remote.loan.ILoanApiService
import com.ivanov.loanonline.data.model.remote.LoanConditionsEntity
import com.ivanov.loanonline.data.model.remote.LoanEntity
import com.ivanov.loanonline.data.model.remote.LoanRequestEntity
import com.ivanov.loanonline.domain.repository.ILoanRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoanRepository @Inject constructor(
    private val loanApiService: ILoanApiService,
    private val dispatcher: CoroutineDispatcher
) : ILoanRepository {
    override suspend fun getConditions(): Result<LoanConditionsEntity> =
        withContext(dispatcher) {
            loanApiService.getConditions()
        }

    override suspend fun createLoan(loanRequest: LoanRequestEntity): Result<LoanEntity> =
        withContext(dispatcher) {
            loanApiService.createLoan(loanRequest)
        }

    override suspend fun getAllLoanRequests(): Result<List<LoanEntity>> =
        withContext(dispatcher) {
            loanApiService.getAllLoanRequests()
        }
}