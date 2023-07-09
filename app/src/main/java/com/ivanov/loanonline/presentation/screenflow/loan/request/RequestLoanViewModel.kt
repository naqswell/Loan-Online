package com.ivanov.loanonline.presentation.screenflow.loan.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivanov.loanonline.core.presentation.lce.Lce
import com.ivanov.loanonline.data.model.local.LoanSumValues
import com.ivanov.loanonline.data.model.remote.LoanConditionsEntity
import com.ivanov.loanonline.data.model.remote.LoanRequestEntity
import com.ivanov.loanonline.domain.InputStatus
import com.ivanov.loanonline.domain.InputValidator
import com.ivanov.loanonline.domain.repository.ILoanRepository
import com.ivanov.loanonline.presentation.common.IMainRouter
import com.ivanov.loanonline.presentation.utils.NetworkErrorStringProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class RequestLoanViewModel @Inject constructor(
    private val router: IMainRouter,
    private val loanRepository: ILoanRepository,
    private val errorStringProvider: NetworkErrorStringProvider
) : ViewModel() {

    companion object {
        const val TAG = "RequestLoanViewModel"
    }

    private val <T> LiveData<T>.mutable: MutableLiveData<T>
        get() = this as MutableLiveData<T>

    val nameInputStatus: LiveData<InputStatus> = MutableLiveData()
    val surnameInputStatus: LiveData<InputStatus> = MutableLiveData()
    val phoneInputStatus: LiveData<InputStatus> = MutableLiveData()
    val selectedSum: LiveData<LoanSumValues> = MutableLiveData()
    val requestStatus: LiveData<Lce<Unit>?> = MutableLiveData()
    private var conditions: LoanConditionsEntity? = null

    fun initViewModel(loanSumValues: LoanSumValues, conditions: LoanConditionsEntity) {
        selectedSum.mutable.value = loanSumValues
        requestStatus.mutable.value = null
        this.conditions = conditions
    }

    fun updateNameInput(name: String) {
        val nameStatus = InputValidator.validateName(name)
        nameInputStatus.mutable.value = nameStatus
    }

    fun updateSurnameInput(name: String) {
        val surnameStatus = InputValidator.validateName(name)
        surnameInputStatus.mutable.value = surnameStatus
    }

    fun updatePhoneInput(name: String) {
        val phoneStatus = InputValidator.validatePhone(name)
        phoneInputStatus.mutable.value = phoneStatus
    }

    fun navigateBack() {
        router.navigateBack()
    }

    fun navigateGuideFragment() {
        router.openGuideScreen()
    }

    fun createNewLoan() {
        val nameStatus = nameInputStatus.value
        val surnameStatus = surnameInputStatus.value
        val phoneInputStatus = phoneInputStatus.value
        val amount = selectedSum.value?.selected

        if (nameStatus is InputStatus.Correct
            && surnameStatus is InputStatus.Correct
            && phoneInputStatus is InputStatus.Correct
        ) {
            viewModelScope.launch {
                amount?.let { amountNotNull ->
                    conditions?.let { conditionsNotNull ->
                        val loanRequest = LoanRequestEntity(
                            amount = amountNotNull,
                            firstName = nameStatus.input,
                            lastName = surnameStatus.input,
                            percent = conditionsNotNull.percent,
                            period = conditionsNotNull.period,
                            phoneNumber = phoneInputStatus.input,
                        )
                        requestStatus.mutable.value = Lce.Loading
                        val response = loanRepository.createLoan(loanRequest)
                        response
                            .onSuccess { requestStatus.mutable.value = Lce.Content(Unit) }
                            .onFailure { t -> requestStatus.mutable.value = Lce.Error(errorStringProvider(t)) }
                    }
                }
            }
        }
    }
}