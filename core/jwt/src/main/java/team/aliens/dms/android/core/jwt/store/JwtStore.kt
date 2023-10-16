package team.aliens.dms.android.core.jwt.store

import team.aliens.dms.android.core.jwt.AccessToken
import team.aliens.dms.android.core.jwt.AccessTokenExpiration
import team.aliens.dms.android.core.jwt.RefreshToken
import team.aliens.dms.android.core.jwt.RefreshTokenExpiration

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
