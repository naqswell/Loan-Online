package com.ivanov.loanonline.presentation.screenflow.loan.main.adapters.loanform

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ivanov.loanonline.data.model.remote.LoanConditionsEntity
import com.ivanov.loanonline.databinding.ItemLoanFormBinding
import com.ivanov.loanonline.data.model.local.LoanSumValues

class LoanFormAdapter : RecyclerView.Adapter<LoanFormViewHolder>() {

    private var loanConditions: LoanConditionsEntity? = null
    private var onBtnClick: ((LoanSumValues) -> Unit)? = null

    fun bindData(loanConditions: LoanConditionsEntity) {
        this.loanConditions = loanConditions
        notifyDataSetChanged()
    }

    fun bindButtonClick(onClick: (LoanSumValues) -> Unit) {
        onBtnClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanFormViewHolder {
        val binding = ItemLoanFormBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoanFormViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: LoanFormViewHolder, position: Int) {
        loanConditions?.let { conditions ->
            holder.bind(conditions, onBtnClick)
        }
    }

}