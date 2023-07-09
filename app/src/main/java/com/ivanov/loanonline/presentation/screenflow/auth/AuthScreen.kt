package com.ivanov.loanonline.presentation.screenflow.auth

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

object AuthScreen : FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment =
        factory.instantiate(ClassLoader.getSystemClassLoader(), AuthFragment::class.java.name)
}