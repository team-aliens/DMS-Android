package team.aliens.dms.android.core.jwt.network.model

import com.google.gson.annotations.SerializedName

data class TokensResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("access_token_expired_at") val accessTokenExpiration: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("refresh_token_expired_at") val refreshTokenExpiration: String,
)
