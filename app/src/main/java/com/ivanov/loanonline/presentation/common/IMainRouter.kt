package com.ivanov.loanonline.presentation.common

import com.ivanov.loanonline.data.model.local.LoanSumValues
import com.ivanov.loanonline.data.model.remote.LoanConditionsEntity

interface IMainRouter {
    fun openLoginScreen()
    fun openLoanScreen()
    fun openGuideScreen()
    fun openRequestLoanScreen(sumValues: LoanSumValues, conditions: LoanConditionsEntity)
    fun openLoanDetailScreen(id: Long)
    fun navigateBack()
}