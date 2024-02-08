package team.aliens.dms.android.core.jwt

import kotlinx.coroutines.flow.StateFlow

abstract class JwtProvider {

    abstract val cachedAccessToken: AccessToken
    abstract val isCachedAccessTokenAvailable: StateFlow<Boolean>
    abstract val cachedRefreshToken: RefreshToken
    abstract val isCachedRefreshTokenAvailable: StateFlow<Boolean>

    abstract fun updateTokens(tokens: Tokens)

    abstract fun clearCaches()
}
