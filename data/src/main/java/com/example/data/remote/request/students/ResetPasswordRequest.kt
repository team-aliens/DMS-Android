package com.example.data.remote.request.students

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("account_id") val accountId: String,
    @SerializedName("auth_code") val authCode: String,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("new_password") val newPassword: String
)