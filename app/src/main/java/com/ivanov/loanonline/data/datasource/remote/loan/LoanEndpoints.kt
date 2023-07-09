package com.ivanov.loanonline.data.datasource.remote.loan

object LoanEndpoints {
    const val BASE_URL = "https://shiftlab.cft.ru:7777"
    const val CREATE_LOAN = "/loans"
    const val LOAN_ALL = "/loans/all"
    const val LOAN_CONDITIONS = "/loans/conditions"
    const val LOAN_BY_ID = "/loans/{id}"
}