package com.ivanov.loanonline.data.datasource.remote.loan

import com.ivanov.loanonline.data.model.remote.LoanConditionsEntity
import com.ivanov.loanonline.data.model.remote.LoanEntity
import com.ivanov.loanonline.data.model.remote.LoanRequestEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ILoanApiService {

    @GET(LoanEndpoints.LOAN_CONDITIONS)
    suspend fun getConditions(): Result<LoanConditionsEntity>

    @GET(LoanEndpoints.LOAN_ALL)
    suspend fun getAllLoanRequests(): Result<List<LoanEntity>>

    @POST(LoanEndpoints.CREATE_LOAN)
    suspend fun createLoan(@Body loanRequest: LoanRequestEntity): Result<LoanEntity>

    @GET(LoanEndpoints.LOAN_BY_ID)
    suspend fun getLoanById(@Path("id") id: Long): Result<LoanEntity>

}