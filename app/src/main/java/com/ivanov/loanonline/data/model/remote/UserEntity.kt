package com.ivanov.loanonline.data.model.remote

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("name") val name: String,
    @SerializedName("role") val role: String
)
