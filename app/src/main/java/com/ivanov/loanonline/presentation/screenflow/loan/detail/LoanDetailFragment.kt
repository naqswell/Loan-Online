package com.ivanov.loanonline.presentation.screenflow.loan.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ivanov.loanonline.R
import com.ivanov.loanonline.data.model.remote.LoanEntity
import com.ivanov.loanonline.data.util.StateResourceResolver
import com.ivanov.loanonline.databinding.FragmentLoanDetailBinding
import com.ivanov.loanonline.core.presentation.lce.Lce
import com.ivanov.loanonline.presentation.screenflow.loan.detail.LoanDetailScreen.Companion.KEY_LOAN_ID
import javax.inject.Inject

class LoanDetailFragment @Inject constructor(
    private val factory: ViewModelProvider.Factory
) : Fragment(R.layout.fragment_loan_detail) {

    private lateinit var viewModel: LoanDetailViewModel
    private var binding: FragmentLoanDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, factory)[LoanDetailViewModel::class.java]
        binding = FragmentLoanDetailBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiStates()
        initBackButton()
        viewModel = ViewModelProvider(this, factory)[LoanDetailViewModel::class.java]
        val id: Long = requireArguments().getLong(KEY_LOAN_ID)
        viewModel.getLoanById(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initBackButton() = binding?.apply {
        toolbar.setNavigationOnClickListener {
            viewModel.goBack()
        }
    }

    private fun initUiStates() = binding?.apply {
        viewModel.loanDetail.observe(viewLifecycleOwner) { lce ->
            when (lce) {
                is Lce.Loading -> {
                    progressbar.visibility = View.VISIBLE
                    rootDetails.visibility = View.GONE
                }

                is Lce.Content -> renderSuccessUi(lce.data)
                is Lce.Error -> {}
            }
        }
    }

    private fun renderSuccessUi(loanDetail: LoanEntity) = binding?.apply {
        progressbar.visibility = View.GONE
        rootDetails.visibility = View.VISIBLE

        txtNameValue.text = loanDetail.firstName
        txtSurnameValue.text = loanDetail.lastName
        txtPhoneHeader.text = loanDetail.phoneNumber
        val sumString =
            requireContext().getString(R.string.rubble_symbol, loanDetail.amount.toInt().toString())
        txtSumValue.text = sumString
        val percentString =
            requireContext().getString(R.string.loan_percent, loanDetail.percent.toString())
        txtPercentValue.text = percentString
        txtPeriodValue.text = loanDetail.period.toString()
        txtStatusValue.text = StateResourceResolver(requireContext(), loanDetail.state)
    }
}