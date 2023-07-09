package com.ivanov.loanonline.presentation.common

import com.github.terrakok.cicerone.Router
import com.ivanov.loanonline.data.model.local.LoanSumValues
import com.ivanov.loanonline.data.model.remote.LoanConditionsEntity
import com.ivanov.loanonline.presentation.screenflow.loan.main.LoanMainScreen
import com.ivanov.loanonline.presentation.screenflow.auth.AuthScreen
import com.ivanov.loanonline.presentation.screenflow.loan.detail.LoanDetailScreen
import com.ivanov.loanonline.presentation.screenflow.loan.guide.GuideScreen
import com.ivanov.loanonline.presentation.screenflow.loan.request.RequestLoanScreen
import javax.inject.Inject

class MainRouter @Inject constructor(
    private val router: Router
) : IMainRouter {

    override fun openLoginScreen() {
        router.navigateTo(AuthScreen)
    }

    override fun openLoanScreen() {
        router.newRootScreen(LoanMainScreen)
    }

    override fun openRequestLoanScreen(sumValues: LoanSumValues, conditions: LoanConditionsEntity) {
        router.navigateTo(RequestLoanScreen(sumValues, conditions))
    }

    override fun openLoanDetailScreen(id: Long) {
        router.navigateTo(LoanDetailScreen(id))
    }

    override fun openGuideScreen() {
        router.navigateTo(GuideScreen)
    }

    override fun navigateBack() {
        router.exit()
    }

}