package com.ivanov.loanonline.data.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoanConditionsEntity(
    @SerializedName("maxAmount") val maxAmount: Float,
    @SerializedName("percent") val percent: Double,
    @SerializedName("period") val period: Int
) : Parcelable