package com.ivanov.loanonline.presentation.screenflow.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ivanov.loanonline.R
import com.ivanov.loanonline.databinding.FragmentAuthBinding
import com.ivanov.loanonline.domain.InputStatus
import javax.inject.Inject

class AuthFragment @Inject constructor(
    private val factory: ViewModelProvider.Factory
) : Fragment(R.layout.fragment_auth) {

    private lateinit var viewModel: AuthViewModel
    private var binding: FragmentAuthBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        binding = FragmentAuthBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNameStatusListener()
        initPasswordStatusListener()
        initRegisterBtnListeners()
        initProgressBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initProgressBar() = binding?.apply {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            progressBar.isVisible = it
            inputRoot.isVisible = !it
        }
    }

    private fun initRegisterBtnListeners() = binding?.apply {
        btnRegister.setOnClickListener {
            updateNameStatus()
            updatePasswordStatus()
            viewModel.sendLoginData()
        }
    }

    private fun updateNameStatus() = binding?.apply {
        val name = edtTxtName.text.toString()
        viewModel.updateNameInput(name)
    }

    private fun updatePasswordStatus() = binding?.apply {
        val password = edtTxtPassword.text.toString()
        viewModel.updatePasswordInput(password)
    }

    private fun initPasswordStatusListener() = binding?.apply {
        viewModel.passwordInputStatus.observe(viewLifecycleOwner) { inputStatus ->
            txtInputPassword.apply {
                error = when (inputStatus) {
                    is InputStatus.ShortLength -> requireContext().getString(R.string.input_status_short)
                    is InputStatus.NoLetter -> requireContext().getString(R.string.input_status_no_letters)
                    is InputStatus.Correct -> null
                }
            }
        }

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

}