package com.ivanov.loanonline.presentation.screenflow.loan.main.adapters.loanform

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.ivanov.loanonline.R
import com.ivanov.loanonline.data.Constants.MIN_SUM
import com.ivanov.loanonline.data.model.remote.LoanConditionsEntity
import com.ivanov.loanonline.databinding.ItemLoanFormBinding
import com.ivanov.loanonline.data.model.local.LoanSumValues

class LoanFormViewHolder(
    private val itemBinding: ItemLoanFormBinding,
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    private val context: Context = itemBinding.root.context

    fun bind(
        loanConditions: LoanConditionsEntity,
        onBtnClick: ((LoanSumValues) -> Unit)? = null
    ) {
        initPercentTxt(loanConditions)
        initPeriodTxt(loanConditions)
        initSlider(loanConditions)
        initMaxSum(loanConditions)
        updateUiSetSelectedSum(MIN_SUM)
        onBtnClick?.let { onClick -> initApplyBtnClick(onClick) }

    }

    private fun initApplyBtnClick(onBtnClick: ((LoanSumValues) -> Unit)) = with(itemBinding) {
        btnApplyLoan.setOnClickListener {
            onBtnClick(getCurrentSum())
        }
    }

    private fun initPercentTxt(loanConditions: LoanConditionsEntity) = with(itemBinding) {
        val percentString =
            context.getString(R.string.loan_percent, loanConditions.percent.toString())
        txtPercent.text = percentString
    }

    private fun initPeriodTxt(loanConditions: LoanConditionsEntity) = with(itemBinding) {
        val periodString =
            context.getString(R.string.loan_period, loanConditions.period.toString())
        txtPeriod.text = periodString
    }


    private fun getCurrentSum(): LoanSumValues = with(itemBinding) {
        LoanSumValues(
            maximum = slider.valueTo.toLong(),
            minimum = slider.valueFrom.toLong(),
            selected = slider.value.toLong()
        )
    }

    private fun initSlider(loanConditions: LoanConditionsEntity) = with(itemBinding) {
        slider.apply {
            value = MIN_SUM
            valueFrom = MIN_SUM
            stepSize = MIN_SUM
            valueTo = loanConditions.maxAmount

            addOnChangeListener { _, value, _ ->
                updateUiSetSelectedSum(value)
            }
        }
    }

    private fun initMaxSum(loanConditions: LoanConditionsEntity) = with(itemBinding) {
        val sumString = context.getString(
            R.string.rubble_symbol, loanConditions.maxAmount.toInt().toString()
        )
        txtMaxSum.text = sumString
    }

    private fun updateUiSetSelectedSum(selectedSum: Float) = with(itemBinding) {
        val sumText = context.getString(R.string.rubble_symbol, selectedSum.toInt().toString())
        txtSelectedSum.text = sumText
    }
}