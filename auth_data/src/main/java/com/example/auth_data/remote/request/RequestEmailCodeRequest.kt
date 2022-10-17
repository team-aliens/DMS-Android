package com.example.auth_data.remote.request

import com.example.auth_domain.enum.EmailType
import com.google.gson.annotations.SerializedName

data class RequestEmailCodeRequest(
    @SerializedName("email") val email: String,
    @SerializedName("type") val type: EmailType
)
