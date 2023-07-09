package com.ivanov.loanonline.presentation.screenflow.loan.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ivanov.loanonline.R
import com.ivanov.loanonline.databinding.FragmentGuideBinding
import javax.inject.Inject

class GuideFragment @Inject constructor(
    private val factory: ViewModelProvider.Factory
): Fragment(R.layout.fragment_guide) {

    private lateinit var viewModel: GuideViewModel
    private var binding: FragmentGuideBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, factory)[GuideViewModel::class.java]
        binding = FragmentGuideBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initBackButton() = binding?.apply {
        toolbar.setNavigationOnClickListener {
            viewModel.navigateBack()
        }
    }
}