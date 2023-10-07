package team.aliens.dms_android.core.jwt

abstract class JwtStore {
    abstract fun loadAccessToken(): AccessToken
    abstract suspend fun storeAccessToken(token: AccessToken)
    abstract fun loadAccessTokenExpiration(): AccessTokenExpiration
    abstract suspend fun storeAccessTokenExpiration(expiration: AccessTokenExpiration)
    abstract fun loadRefreshToken(): RefreshToken
    abstract suspend fun storeRefreshToken(token: RefreshToken)
    abstract fun loadRefreshTokenExpiration(): RefreshTokenExpiration
    abstract suspend fun storeRefreshTokenExpiration(expiration: RefreshTokenExpiration)
}
