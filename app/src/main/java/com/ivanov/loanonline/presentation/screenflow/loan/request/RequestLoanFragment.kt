package com.ivanov.loanonline.presentation.screenflow.loan.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ivanov.loanonline.R
import com.ivanov.loanonline.core.presentation.lce.Lce
import com.ivanov.loanonline.data.model.local.LoanSumValues
import com.ivanov.loanonline.data.model.remote.LoanConditionsEntity
import com.ivanov.loanonline.databinding.FragmentRequestLoanBinding
import com.ivanov.loanonline.domain.InputStatus
import com.ivanov.loanonline.presentation.screenflow.loan.request.RequestLoanScreen.Companion.KEY_CONDITIONS
import com.ivanov.loanonline.presentation.screenflow.loan.request.RequestLoanScreen.Companion.KEY_SELECTED_SUM
import com.ivanov.loanonline.presentation.utils.parcelable
import com.ivanov.loanonline.presentation.utils.showSnack
import javax.inject.Inject

class RequestLoanFragment @Inject constructor(
    private val factory: ViewModelProvider.Factory,
) : Fragment(R.layout.fragment_request_loan) {

    private lateinit var viewModel: RequestLoanViewModel
    private var binding: FragmentRequestLoanBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, factory)[RequestLoanViewModel::class.java]
        binding = FragmentRequestLoanBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackButton()
        initRegisterBtnListeners()
        initNameStatusListener()
        initSelectedSum()
        initSurnameStatusListener()
        initPhoneStatusListener()
        initSliderListener()
        initShowSuccessRequestUiListener()
        initSuccessBtnListener()
        initHelpButton()

        val conditions: LoanConditionsEntity? = requireArguments().parcelable(KEY_CONDITIONS)
        val loanSumValues: LoanSumValues? = requireArguments().parcelable(KEY_SELECTED_SUM)

        loanSumValues?.let { loanSum ->
            conditions?.let { cond ->
                viewModel.initViewModel(loanSum, cond)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initHelpButton() = binding?.apply {
        btnHelp.setOnClickListener {
            viewModel.navigateGuideFragment()
        }
    }

    private fun initSliderListener() = binding?.apply {
        viewModel.selectedSum.observe(viewLifecycleOwner) { sum ->
            updateUiSetSelectedSum(sum.selected.toFloat())
            slider.valueFrom = sum.minimum.toFloat()
            slider.valueTo = sum.maximum.toFloat()
            slider.value = sum.selected.toFloat()
            slider.stepSize = sum.minimum.toFloat()
            txtMaxSum.text = sum.maximum.toString()
            slider.isEnabled = false
        }
    }

    private fun updateUiSetSelectedSum(selectedSum: Float) = binding?.apply {
        val sumText =
            requireContext().getString(R.string.rubble_symbol, selectedSum.toInt().toString())
        txtSelectedSum.text = sumText
    }

    private fun initSelectedSum() = binding?.apply {
        viewModel.selectedSum.observe(viewLifecycleOwner) { selectedSum ->
            selectedSum?.let {
                val sumText = requireContext().getString(
                    R.string.rubble_symbol,
                    selectedSum.selected.toString()
                )
                txtSelectedSum.text = sumText
                slider.valueFrom = selectedSum.minimum.toFloat()
                slider.valueTo = selectedSum.maximum.toFloat()
                slider.value = selectedSum.selected.toFloat()
                slider.stepSize = selectedSum.minimum.toFloat()
            }
        }
    }

    private fun initBackButton() = binding?.apply {
        toolbar.setNavigationOnClickListener {
            clearTextFields()
            viewModel.navigateBack()
        }
    }

    private fun initRegisterBtnListeners() = binding?.apply {
        btnApplyLoan.setOnClickListener {
            updateUiNameStatus()
            updateUiSurnameStatus()
            updateUiPhoneStatus()
            viewModel.createNewLoan()
        }
    }

    private fun updateUiNameStatus() = binding?.apply {
        val name = edtTxtName.text.toString()
        viewModel.updateNameInput(name)
    }

    private fun updateUiSurnameStatus() = binding?.apply {
        val surname = edtTxtSurname.text.toString()
        viewModel.updateSurnameInput(surname)
    }

    private fun updateUiPhoneStatus() = binding?.apply {
        val name = edtTxtSPhone.text.toString()
        viewModel.updatePhoneInput(name)
    }

    private fun initNameStatusListener() = binding?.apply {
        viewModel.nameInputStatus.observe(viewLifecycleOwner) { inputStatus ->
            txtInputName.apply {
                error = when (inputStatus) {
                    is InputStatus.ShortLength -> requireContext().getString(R.string.input_status_short)
                    is InputStatus.NoLetter -> requireContext().getString(R.string.input_status_no_letters)
                    is InputStatus.Correct -> null
                }
            }
        }
    }

    private fun initSurnameStatusListener() = binding?.apply {
        viewModel.surnameInputStatus.observe(viewLifecycleOwner) { inputStatus ->
            txtInputSurname.apply {
                error = when (inputStatus) {
                    is InputStatus.ShortLength -> requireContext().getString(R.string.input_status_short)
                    is InputStatus.NoLetter -> requireContext().getString(R.string.input_status_no_letters)
                    is InputStatus.Correct -> null
                }
            }
        }
    }

    private fun initPhoneStatusListener() = binding?.apply {
        viewModel.phoneInputStatus.observe(viewLifecycleOwner) { inputStatus ->
            txtInputPhone.apply {
                error = when (inputStatus) {
                    is InputStatus.ShortLength -> requireContext().getString(R.string.input_status_short)
                    is InputStatus.NoLetter -> requireContext().getString(R.string.input_status_no_letters)
                    is InputStatus.Correct -> null
                }
            }
        }
    }

    private fun initSuccessBtnListener() = binding?.apply {
        btnSuccess.setOnClickListener {
            clearTextFields()
            viewModel.navigateBack()
        }
    }

    private fun initShowSuccessRequestUiListener() = binding?.apply {
        viewModel.requestStatus.observe(viewLifecycleOwner) { lce ->
            when (lce) {
                is Lce.Content -> {
                    clearTextFields()
                    requestLayout.visibility = View.GONE
                    successLayout.visibility = View.VISIBLE
                    btnApplyLoan.isEnabled = true
                    btnApplyLoan.isClickable = true
                }

                is Lce.Error -> {
                    showErrorToast(this.root, lce.errorString)
                    requestLayout.visibility = View.VISIBLE
                    successLayout.visibility = View.GONE
                    btnApplyLoan.isEnabled = true
                    btnApplyLoan.isClickable = true
                }

                is Lce.Loading -> {
                    btnApplyLoan.isEnabled = false
                    btnApplyLoan.isClickable = false
                }

                else -> {
                    requestLayout.visibility = View.VISIBLE
                    successLayout.visibility = View.GONE
                    btnApplyLoan.isEnabled = true
                    btnApplyLoan.isClickable = true
                }
            }
        }
    }

    private fun showErrorToast(view: View, errorString: String) {
        view.showSnack(errorString)
    }

    private fun clearTextFields() = binding?.apply {
        edtTxtName.text?.clear()
        edtTxtSurname.text?.clear()
        edtTxtSPhone.text?.clear()
    }

}