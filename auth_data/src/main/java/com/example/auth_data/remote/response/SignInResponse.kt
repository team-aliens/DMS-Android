package com.example.auth_data.remote.response

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("surveyBoolean") val surveyBoolean: Boolean,
    @SerializedName("noticeBoolean") val noticeBoolean: Boolean,
    @SerializedName("myPageBoolean") val myPageBoolean: Boolean,
    @SerializedName("recentRoomBoolean") val recentBoolean: Boolean,
)
