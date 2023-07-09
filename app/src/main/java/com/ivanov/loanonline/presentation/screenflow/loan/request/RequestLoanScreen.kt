package com.ivanov.loanonline.presentation.screenflow.loan.request

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ivanov.loanonline.data.model.local.LoanSumValues
import com.ivanov.loanonline.data.model.remote.LoanConditionsEntity

class RequestLoanScreen(
    private val sumValues: LoanSumValues,
    private val conditions: LoanConditionsEntity
) : FragmentScreen {

    companion object {
        const val KEY_SELECTED_SUM = "selectedSumKey"
        const val KEY_CONDITIONS = "conditionsKey"
    }

    override fun createFragment(factory: FragmentFactory): Fragment {
        val fragment = factory.instantiate(
            ClassLoader.getSystemClassLoader(),
            RequestLoanFragment::class.java.name
        )
        return fragment.apply {
            arguments = Bundle().apply {
                putParcelable(KEY_SELECTED_SUM, sumValues)
                putParcelable(KEY_CONDITIONS, conditions)
            }
        }
    }

}