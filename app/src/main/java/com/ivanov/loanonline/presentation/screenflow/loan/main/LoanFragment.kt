package com.ivanov.loanonline.presentation.screenflow.loan.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ivanov.loanonline.R
import com.ivanov.loanonline.core.presentation.lce.Lce
import com.ivanov.loanonline.databinding.FragmentLoanBinding
import com.ivanov.loanonline.presentation.screenflow.loan.main.adapters.history.LoanHistoryItemAdapter
import com.ivanov.loanonline.presentation.screenflow.loan.main.adapters.loanform.LoanFormAdapter
import com.ivanov.loanonline.presentation.utils.showSnack
import javax.inject.Inject


class LoanFragment @Inject constructor(
    private val factory: ViewModelProvider.Factory
) : Fragment(R.layout.fragment_loan) {

    private lateinit var viewModel: LoanFormViewModel
    private var binding: FragmentLoanBinding? = null
    private val loanFormAdapter by lazy { LoanFormAdapter() }
    private val loanHistoryItemAdapter by lazy { LoanHistoryItemAdapter() }
    private val concatAdapter by lazy { ConcatAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, factory)[LoanFormViewModel::class.java]
        binding = FragmentLoanBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoanFormAdapter()
        initHistoryAdapter()
        initRecyclerView()
        initRefreshListener()
        refreshData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun refreshData() {
        viewModel.fetchConditions()
        viewModel.fetchHistory()
    }

    private fun initRefreshListener() = binding?.apply {
        swiperRefresher.setOnRefreshListener {
            refreshData()
            swiperRefresher.isRefreshing = false
        }
    }

    private fun initRecyclerView() = binding?.apply {
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = concatAdapter
    }

    private fun initHistoryAdapter() {
        concatAdapter.addAdapter(loanHistoryItemAdapter)
        viewModel.history.observe(viewLifecycleOwner) { lce ->
            when (lce) {
                is Lce.Content -> {
                    loanHistoryItemAdapter.bindData(lce.data) { id ->
                        viewModel.navigateLoanDetail(id)
                    }
                }

                is Lce.Loading -> {}
                is Lce.Error -> {
                    binding?.root?.showSnack(lce.errorString)
                }
            }
        }
    }

    private fun initLoanFormAdapter() {
        concatAdapter.addAdapter(loanFormAdapter)
        viewModel.conditions.observe(viewLifecycleOwner) { lce ->
            when (lce) {
                is Lce.Content -> {
                    loanFormAdapter.bindData(lce.data)
                    renderUiLoanFormSuccess()
                }

                is Lce.Loading -> {}
                is Lce.Error -> {
                    binding?.root?.showSnack(lce.errorString)
                }
            }
        }
    }

    private fun renderUiLoanFormSuccess() {
        loanFormAdapter.bindButtonClick { selectedSum ->
            viewModel.updateSelectedSum(selectedSum)
            viewModel.conditions.observe(viewLifecycleOwner) { lce ->
                if (lce is Lce.Content) {
                    viewModel.navigateRequestLoan(selectedSum, lce.data)
                }
            }
        }
    }
}