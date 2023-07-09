package com.ivanov.loanonline.presentation.screenflow.loan.guide

import androidx.lifecycle.ViewModel
import com.ivanov.loanonline.presentation.common.IMainRouter
import javax.inject.Inject

class GuideViewModel @Inject constructor(
    private val router: IMainRouter
) : ViewModel() {

    fun navigateBack() {
        router.navigateBack()
    }
}