package com.ivanov.loanonline.data.model.remote

import com.google.gson.annotations.SerializedName

data class LoginEntity(
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String
)
