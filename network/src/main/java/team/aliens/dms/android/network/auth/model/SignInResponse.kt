package team.aliens.dms.android.network.auth.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class SignInResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("access_token_expired_at") val accessTokenExpiration: LocalDateTime,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("refresh_token_expired_at") val refreshTokenExpiration: LocalDateTime,
    @SerializedName("features") val features: List<FeaturesResponse>,
) {
    data class FeaturesResponse(
        @SerializedName("meal_service") val mealService: Boolean,
        @SerializedName("notice_service") val noticeService: Boolean,
        @SerializedName("point_service") val pointService: Boolean,
        @SerializedName("study_room_service") val studyRoomService: Boolean,
        @SerializedName("remain_service") val remainsService: Boolean,
    )
}
