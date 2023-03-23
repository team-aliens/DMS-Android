package team.aliens.remote.model._common

import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("access_token_expired_at") val accessTokenExpiredAt: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("refresh_token_expired_at") val refreshTokenExpiredAt: String,
    @SerializedName("features") val features: FeaturesResponse,
)
