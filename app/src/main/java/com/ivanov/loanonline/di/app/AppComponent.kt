package com.ivanov.loanonline.di.app

import android.content.Context
import com.ivanov.loanonline.App
import com.ivanov.loanonline.di.data.DataModule
import com.ivanov.loanonline.di.presentation.PresentationModule
import com.ivanov.loanonline.presentation.screenflow.loan.LoanHostActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        PresentationModule::class,
        DataModule::class]
)
@AppScope
interface AppComponent {

    fun inject(app: App)
    fun inject(activity: LoanHostActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}