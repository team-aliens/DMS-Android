package com.example.auth_data.remote.response

import com.google.gson.annotations.SerializedName

data class IdResponse(
    @SerializedName("email") val email: String,
)
