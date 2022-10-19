package com.example.auth_data.remote.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("school_code") val schoolCode: String,
    @SerializedName("school_answer") val schoolAnswer: String,
    @SerializedName("email") val email: String,
    @SerializedName("auth_code") val authCode: String,
    @SerializedName("grade") val grade: Int,
    @SerializedName("number") val number: Int,
    @SerializedName("account_id") val accountId: String,
    @SerializedName("password") val password: String,
    @SerializedName("profile_image_url") val profileImageUrl: String?,
)
