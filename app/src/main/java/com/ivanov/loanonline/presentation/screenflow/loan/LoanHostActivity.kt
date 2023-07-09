package com.ivanov.loanonline.presentation.screenflow.loan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.ivanov.loanonline.App
import com.ivanov.loanonline.R
import com.ivanov.loanonline.data.repository.SettingsRepository
import com.ivanov.loanonline.di.presentation.fragment.FragmentFactory
import com.ivanov.loanonline.presentation.screenflow.auth.AuthScreen
import com.ivanov.loanonline.presentation.screenflow.loan.main.LoanMainScreen
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoanHostActivity : AppCompatActivity() {

    @Inject lateinit var router: Router
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var fragmentFactory: FragmentFactory
    @Inject lateinit var settingsRepository: SettingsRepository

    private val navigator by lazy {
        AppNavigator(
            this,
            R.id.loan_host_container,
            fragmentFactory = fragmentFactory
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.INSTANCE.appComponent.inject(this)
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_loan)
        if (savedInstanceState == null) {
            openFragmentDependOnLogin()
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    private fun openFragmentDependOnLogin() {
        lifecycleScope.launch {
            val token = settingsRepository.getCredential().firstOrNull()
            if (token == null) {
                router.newRootScreen(AuthScreen)
            } else {
                router.newRootScreen(LoanMainScreen)
            }
        }
    }
}