package team.aliens.dms.android.core.jwt

import kotlinx.coroutines.flow.StateFlow

abstract class JwtProvider {

    abstract val cachedAccessToken: AccessToken
    abstract val cachedAccessTokenExpiration: AccessTokenExpiration
    abstract val isCachedAccessTokenAvailable: StateFlow<Boolean>
    abstract val cachedRefreshToken: RefreshToken
    abstract val cachedRefreshTokenExpiration: RefreshTokenExpiration
    abstract val isCachedRefreshTokenAvailable: StateFlow<Boolean>

    abstract fun saveTokens(tokens: Tokens)
}
