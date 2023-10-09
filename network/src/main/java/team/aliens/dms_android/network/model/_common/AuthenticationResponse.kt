package team.aliens.dms_android.network.model._common

import com.google.gson.annotations.SerializedName
import team.aliens.dms_android.domain.model._common.AuthenticationOutput

data class AuthenticationResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("access_token_expired_at") val accessTokenExpiredAt: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("refresh_token_expired_at") val refreshTokenExpiredAt: String,
    @SerializedName("features") val features: FeaturesResponse,
)

internal fun AuthenticationResponse.toDomain(): _root_ide_package_.team.aliens.dms_android.domain.model._common.AuthenticationOutput {
    return _root_ide_package_.team.aliens.dms_android.domain.model._common.AuthenticationOutput(
        accessToken = accessToken,
        accessTokenExpiredAt = accessTokenExpiredAt,
        refreshToken = refreshToken,
        refreshTokenExpiredAt = refreshTokenExpiredAt,
        features = _root_ide_package_.team.aliens.dms_android.domain.model._common.AuthenticationOutput.Features(
            mealService = features.mealService,
            noticeService = features.noticeService,
            pointService = features.pointService,
            studyRoomService = features.studyRoomService,
            remainsService = features.remainsService
        ),
    )
}
