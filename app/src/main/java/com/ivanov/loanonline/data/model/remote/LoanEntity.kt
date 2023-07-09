package com.ivanov.loanonline.data.model.remote

import com.google.gson.annotations.SerializedName

enum class State(val status: String) {
    @SerializedName("APPROVED")
    APPROVED("APPROVED"),

    @SerializedName("REGISTERED")
    REGISTERED("REGISTERED"),

    @SerializedName("REJECTED")
    REJECTED("REJECTED")
}

data class LoanEntity(
    @SerializedName("date") val date: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("amount") val amount: Long,
    @SerializedName("percent") val percent: Double,
    @SerializedName("period") val period: Int,
    @SerializedName("state") val state: State,
    @SerializedName("id") val id: Long,
)