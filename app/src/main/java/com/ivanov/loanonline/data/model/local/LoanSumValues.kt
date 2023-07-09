package com.ivanov.loanonline.data.model.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoanSumValues(
    val minimum: Long,
    val maximum: Long,
    val selected: Long
) : Parcelable
