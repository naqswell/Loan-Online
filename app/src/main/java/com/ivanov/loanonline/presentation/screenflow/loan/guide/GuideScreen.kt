package com.ivanov.loanonline.presentation.screenflow.loan.guide

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

object GuideScreen : FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment =
        factory.instantiate(ClassLoader.getSystemClassLoader(), GuideFragment::class.java.name)

}