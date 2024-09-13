package team.aliens.dms.android.core.jwt

import kotlinx.coroutines.flow.StateFlow

abstract class JwtProvider {

    /**
     * represents cached access token
     */
    abstract val cachedAccessToken: AccessToken

    /**
     * represents if the cached access token is available
     */
    abstract val isCachedAccessTokenAvailable: StateFlow<Boolean>

    /**
     * represents cached refresh token
     */
    abstract val cachedRefreshToken: RefreshToken

    /**
     * represents if the cached refresh token is available
     */
    abstract val isCachedRefreshTokenAvailable: StateFlow<Boolean>

    /**
     * updates tokens on [JwtProvider] itself and device's local storage
     */
    abstract fun updateTokens(tokens: Tokens)

    /**
     * clears tokens on [JwtProvider] itself and device's local storage
     */
    abstract fun clearCaches()
}
