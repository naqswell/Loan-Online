package com.ivanov.loanonline.di.presentation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.ivanov.loanonline.di.app.AppScope
import com.ivanov.loanonline.di.presentation.fragment.FragmentKey
import com.ivanov.loanonline.di.presentation.viewmodel.ViewModelFactory
import com.ivanov.loanonline.di.presentation.viewmodel.ViewModelKey
import com.ivanov.loanonline.presentation.common.IMainRouter
import com.ivanov.loanonline.presentation.common.MainRouter
import com.ivanov.loanonline.presentation.screenflow.loan.main.LoanFragment
import com.ivanov.loanonline.presentation.screenflow.loan.main.LoanFormViewModel
import com.ivanov.loanonline.presentation.screenflow.auth.AuthFragment
import com.ivanov.loanonline.presentation.screenflow.auth.AuthViewModel
import com.ivanov.loanonline.presentation.screenflow.loan.detail.LoanDetailFragment
import com.ivanov.loanonline.presentation.screenflow.loan.detail.LoanDetailViewModel
import com.ivanov.loanonline.presentation.screenflow.loan.guide.GuideFragment
import com.ivanov.loanonline.presentation.screenflow.loan.guide.GuideViewModel
import com.ivanov.loanonline.presentation.screenflow.loan.request.RequestLoanFragment
import com.ivanov.loanonline.presentation.screenflow.loan.request.RequestLoanViewModel
import com.ivanov.loanonline.presentation.utils.NetworkErrorStringProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface PresentationModule {

    companion object {

        @Provides
        @AppScope
        fun newtworkUiHandler(context: Context): NetworkErrorStringProvider = NetworkErrorStringProvider(context.resources)

        @Provides
        @AppScope
        fun provideCicerone() = Cicerone.create()

        @Provides
        fun provideRouter(cicerone: Cicerone<Router>) = cicerone.router

        @Provides
        fun provideNavigatorHolder(cicerone: Cicerone<Router>) = cicerone.getNavigatorHolder()
    }

    @Binds
    fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun mainRouter(router: MainRouter): IMainRouter

    @Binds
    @IntoMap
    @FragmentKey(AuthFragment::class)
    fun loginFragment(fragment: AuthFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun loginViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @FragmentKey(LoanFragment::class)
    fun loanFragment(fragment: LoanFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(LoanFormViewModel::class)
    fun loanViewModel(viewModel: LoanFormViewModel): ViewModel

    @Binds
    @IntoMap
    @FragmentKey(RequestLoanFragment::class)
    fun requestLoanFragment(fragment: RequestLoanFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(RequestLoanViewModel::class)
    fun requestLoanViewModel(viewModel: RequestLoanViewModel): ViewModel

    @Binds
    @IntoMap
    @FragmentKey(LoanDetailFragment::class)
    fun loanDetailFragmetn(fragment: LoanDetailFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(LoanDetailViewModel::class)
    fun loanDetailViewModel(viewModel: LoanDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @FragmentKey(GuideFragment::class)
    fun guideFragment(fragment: GuideFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(GuideViewModel::class)
    fun guideViewModel(ViewModel: GuideViewModel): ViewModel
}