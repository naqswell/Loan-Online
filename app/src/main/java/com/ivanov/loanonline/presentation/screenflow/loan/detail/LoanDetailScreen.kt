package com.ivanov.loanonline.presentation.screenflow.loan.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

class LoanDetailScreen(
    private val loanId: Long
) : FragmentScreen {

    companion object {
        const val KEY_LOAN_ID = "loanIdKey"
    }

    override fun createFragment(factory: FragmentFactory): Fragment {
        val fragment = factory.instantiate(
            ClassLoader.getSystemClassLoader(),
            LoanDetailFragment::class.java.name
        )
        return fragment.apply {
            arguments = Bundle().apply {
                putLong(KEY_LOAN_ID, loanId)
            }
        }
    }

}