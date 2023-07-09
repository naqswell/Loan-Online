package com.ivanov.loanonline.presentation.screenflow.loan.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivanov.loanonline.data.datasource.remote.loan.ILoanApiService
import com.ivanov.loanonline.data.model.remote.LoanEntity
import com.ivanov.loanonline.presentation.common.IMainRouter
import com.ivanov.loanonline.core.presentation.lce.Lce
import com.ivanov.loanonline.presentation.utils.NetworkErrorStringProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoanDetailViewModel @Inject constructor(
    private val router: IMainRouter,
    private val loanApiService: ILoanApiService,
    private val errorStringProvider: NetworkErrorStringProvider
) : ViewModel() {

    companion object {
        const val TAG = "LoanDetailViewModel"
    }

    val loanDetail: LiveData<Lce<LoanEntity>> = MutableLiveData(Lce.Loading)

    private val <T> LiveData<T>.mutable: MutableLiveData<T>
        get() = this as MutableLiveData<T>

    fun getLoanById(id: Long) {
        viewModelScope.launch {
            loanApiService.getLoanById(id)
                .onSuccess { detail -> loanDetail.mutable.value = Lce.Content(detail) }
                .onFailure { t -> Lce.Error(errorStringProvider(t)) }
        }
    }

    fun goBack() {
        router.navigateBack()
    }

}