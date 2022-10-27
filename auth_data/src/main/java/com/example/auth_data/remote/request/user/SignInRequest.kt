package com.example.auth_data.remote.request.user

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("id") val id: String,
    @SerializedName("password") val password: String,
)
