package com.example.data.remote.response.students

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("access_token_expired_at") val accessTokenExpiredAt: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("refresh_token_expired_at") val refreshTokenExpiredAt: String,
    @SerializedName("features") val features: Features,
) {
    data class Features(
        @SerializedName("meal_service") val mealService: Boolean,
        @SerializedName("notice_service") val noticeService: Boolean,
        @SerializedName("point_service") val pointService: Boolean,
        @SerializedName("study_room_service") val studyRoomService: Boolean,
    )
}