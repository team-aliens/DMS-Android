package com.example.auth_data.remote.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("name") val name: String,
    @SerializedName("account_id") val accountId: String,
    @SerializedName("password") val password: String,
    @SerializedName("profile_image_url") val profileImageUrl: String?,
)
