package team.aliens.dms_android.core.jwt.network

import com.google.gson.annotations.SerializedName
import team.aliens.dms_android.core.jwt.Authentication

internal data class AuthenticationResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("access_token_expired_at") val accessTokenExpiration: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("refresh_token_expired_at") val refreshTokenExpiration: String,
    @SerializedName("features") val features: FeaturesResponse,
) {
    data class FeaturesResponse(
        @SerializedName("meal_service") val mealService: Boolean,
        @SerializedName("notice_service") val noticeService: Boolean,
        @SerializedName("point_service") val pointService: Boolean,
        @SerializedName("study_room_service") val studyRoomService: Boolean,
        @SerializedName("remain_service") val remainsService: Boolean,
    )
}

internal fun AuthenticationResponse.toModel(): Authentication = Authentication(
    accessToken = this.accessToken,
    accessTokenExpiration = this.accessTokenExpiration,
    refreshToken = this.refreshToken,
    refreshTokenExpiration = this.refreshTokenExpiration,
    features = this.features.toModel(),
)

internal fun AuthenticationResponse.FeaturesResponse.toModel(): Authentication.Features =
    Authentication.Features(
        mealService = this.mealService,
        noticeService = this.noticeService,
        pointService = this.pointService,
        studyRoomService = this.studyRoomService,
        remainsService = this.remainsService
    )