package com.ivanov.loanonline.data.model.remote

import com.google.gson.annotations.SerializedName

data class LoanRequestEntity(
    @SerializedName("amount") val amount: Long,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("percent") val percent: Double,
    @SerializedName("period") val period: Int,
    @SerializedName("phoneNumber") val phoneNumber: String,
)