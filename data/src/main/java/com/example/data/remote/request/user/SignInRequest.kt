package com.example.data.remote.request.user

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("account_id") val id: String,
    @SerializedName("password") val password: String,
)
