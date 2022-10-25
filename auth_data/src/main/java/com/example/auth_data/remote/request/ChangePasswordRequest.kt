package com.example.auth_data.remote.request

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("account_id") val accountId: String,
    @SerializedName("email") val email: String,
    @SerializedName("auth_code") val authCode: String,
    @SerializedName("new_password") val newPassword: String,
)
