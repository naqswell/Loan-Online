package com.ivanov.loanonline.presentation.screenflow.loan.main.adapters.history

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.ivanov.loanonline.R
import com.ivanov.loanonline.data.model.remote.LoanEntity
import com.ivanov.loanonline.data.util.StateResourceResolver
import com.ivanov.loanonline.databinding.ItemLoanHistoryBinding

class LoanHistoryViewHolder(
    private val itemBinding: ItemLoanHistoryBinding,
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    private val context: Context = itemBinding.root.context

    fun bind(
        loanItem: LoanEntity,
        onBtnClick: ((id: Long) -> Unit)? = null
    ) {
        onBtnClick?.let { click -> initBtnClick(loanItem, click) }
        initTextViews(loanItem)
    }

    private fun initBtnClick(loanItem: LoanEntity, onBtnClick: (id: Long) -> Unit) =
        with(itemBinding) {
            btnDetail.setOnClickListener {
                onBtnClick.invoke(loanItem.id)
            }
        }

    private fun initTextViews(loanItem: LoanEntity) = with(itemBinding) {
        val sumText = context.getString(R.string.rubble_symbol, loanItem.amount.toInt().toString())
        txtSumValue.text = sumText
        val percentString = context.getString(R.string.loan_percent, loanItem.percent.toString())
        txtPercentValue.text = percentString
        txtPeriodValue.text = loanItem.period.toString()
        txtStatusValue.text = StateResourceResolver.invoke(context, loanItem.state)
    }
}