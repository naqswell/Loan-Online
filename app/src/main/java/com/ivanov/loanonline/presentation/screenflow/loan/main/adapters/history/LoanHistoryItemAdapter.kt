package com.ivanov.loanonline.presentation.screenflow.loan.main.adapters.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ivanov.loanonline.data.model.remote.LoanEntity
import com.ivanov.loanonline.databinding.ItemLoanHistoryBinding

class LoanHistoryItemAdapter : RecyclerView.Adapter<LoanHistoryViewHolder>() {

    private var historyList: List<LoanEntity> = listOf()
    private var onBtnClick: ((id: Long) -> Unit)? = null

    fun bindData(historyList: List<LoanEntity>, onClick: (id: Long) -> Unit) {
        this.historyList = historyList
        onBtnClick = onClick
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanHistoryViewHolder {
        val binding = ItemLoanHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoanHistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = historyList.size

    override fun onBindViewHolder(holder: LoanHistoryViewHolder, position: Int) {
        holder.bind(historyList[position], onBtnClick)
    }

}