package team.aliens.dms.android.data.auth.mapper

import team.aliens.dms.android.core.jwt.Tokens
import team.aliens.dms.android.network.auth.model.SignInResponse
import team.aliens.dms.android.shared.date.toLocalDateTime

fun SignInResponse.extractTokens(): Tokens = Tokens(
    accessToken = accessToken,
    accessTokenExpiration = accessTokenExpiration.toLocalDateTime(),
    refreshToken = refreshToken,
    refreshTokenExpiration = refreshTokenExpiration.toLocalDateTime(),
)

// TODO: fun SignInResponse.extractFeatures():Features
