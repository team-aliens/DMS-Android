package com.example.auth_data.remote.response.user

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class SignInResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("access_token_expired_at") val accessTokenExpiredAt: LocalDateTime,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("refresh_token_expired_at") val refreshTokenExpiredAt: LocalDateTime,
    @SerializedName("features") val features: Features,
) {
    data class Features(
        @SerializedName("meal_service") val mealService: Boolean,
        @SerializedName("notice_service") val noticeService: Boolean,
        @SerializedName("point_service") val pointService: Boolean,
    )
}
