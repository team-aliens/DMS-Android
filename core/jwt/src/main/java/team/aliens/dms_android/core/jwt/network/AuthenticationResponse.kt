package team.aliens.dms_android.core.jwt.network

import com.google.gson.annotations.SerializedName
import team.aliens.dms_android.core.jwt.Authentication

internal data class AuthenticationResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("access_token_expired_at") val accessTokenExpiration: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("refresh_token_expired_at") val refreshTokenExpiration: String,
)

internal fun AuthenticationResponse.toModel(): Authentication = Authentication(
    accessToken = this.accessToken,
    accessTokenExpiration = this.accessTokenExpiration,
    refreshToken = this.refreshToken,
    refreshTokenExpiration = this.refreshTokenExpiration,
)
