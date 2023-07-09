package com.ivanov.loanonline.presentation.screenflow.loan.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivanov.loanonline.data.model.local.LoanSumValues
import com.ivanov.loanonline.data.model.remote.LoanConditionsEntity
import com.ivanov.loanonline.data.model.remote.LoanEntity
import com.ivanov.loanonline.domain.repository.ILoanRepository
import com.ivanov.loanonline.presentation.common.IMainRouter
import com.ivanov.loanonline.core.presentation.lce.Lce
import com.ivanov.loanonline.presentation.utils.NetworkErrorStringProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoanFormViewModel @Inject constructor(
    private val router: IMainRouter,
    private val loanRepository: ILoanRepository,
    private val errorStringProvider: NetworkErrorStringProvider
) : ViewModel() {

    companion object {
        const val TAG = "LoanFormViewModel"
    }

    private val <T> LiveData<T>.mutable: MutableLiveData<T>
        get() = this as MutableLiveData<T>

    val conditions: LiveData<Lce<LoanConditionsEntity>> = MutableLiveData(Lce.Loading)
    val selectedSum: LiveData<LoanSumValues> = MutableLiveData()
    val history: LiveData<Lce<List<LoanEntity>>> = MutableLiveData(Lce.Loading)

    fun updateSelectedSum(newSum: LoanSumValues) {
        selectedSum.mutable.value = newSum
    }

    fun fetchConditions() {
        viewModelScope.launch {
            loanRepository.getConditions()
                .onSuccess { response -> conditions.mutable.value = Lce.Content(response) }
                .onFailure { t ->
                    conditions.mutable.value = Lce.Error(errorStringProvider(t))
                }
        }
    }

    fun fetchHistory() {
        viewModelScope.launch {
            loanRepository.getAllLoanRequests()
                .onSuccess { list -> history.mutable.value = Lce.Content(list) }
                .onFailure { t -> history.mutable.value = Lce.Error(errorStringProvider(t)) }
        }
    }

    fun navigateLoanDetail(id: Long) {
        router.openLoanDetailScreen(id)
    }

    fun navigateRequestLoan(sumValues: LoanSumValues, conditionsEntity: LoanConditionsEntity) {
        router.openRequestLoanScreen(sumValues, conditionsEntity)
    }

}