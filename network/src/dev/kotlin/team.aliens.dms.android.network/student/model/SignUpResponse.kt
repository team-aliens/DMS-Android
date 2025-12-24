package team.aliens.dms.android.network.student.model

import com.google.gson.annotations.SerializedName
import team.aliens.dms.android.core.jwt.AccessToken
import team.aliens.dms.android.core.jwt.RefreshToken
import team.aliens.dms.android.core.jwt.Tokens
import team.aliens.dms.android.core.school.Features
import team.aliens.dms.android.shared.date.toLocalDateTime

data class SignUpResponse(
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

fun SignUpResponse.extractTokens(): Tokens = Tokens(
    accessToken = AccessToken(
        value = this.accessToken,
        expiration = this.accessTokenExpiration.toLocalDateTime(),
    ),
    refreshToken = RefreshToken(
        value = this.refreshToken,
        expiration = this.refreshTokenExpiration.toLocalDateTime(),
    ),
)

fun SignUpResponse.extractFeatures(): Features = features.run {
    Features(
        mealService = mealService,
        noticeService = noticeService,
        pointService = pointService,
        studyRoomService = studyRoomService,
        remainsService = remainsService,
    )
}
