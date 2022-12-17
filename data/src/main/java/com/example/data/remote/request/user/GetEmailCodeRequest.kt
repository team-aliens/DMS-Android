package com.example.data.remote.request.user

import com.example.domain.enums.EmailType
import com.google.gson.annotations.SerializedName

data class GetEmailCodeRequest(
    @SerializedName("email") val email: String,
    @SerializedName("type") val type: EmailType,
)
