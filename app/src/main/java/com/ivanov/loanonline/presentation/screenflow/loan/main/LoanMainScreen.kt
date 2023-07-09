package com.ivanov.loanonline.presentation.screenflow.loan.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

object LoanMainScreen : FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment =
        factory.instantiate(ClassLoader.getSystemClassLoader(), LoanFragment::class.java.name)

}