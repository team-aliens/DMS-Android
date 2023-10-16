package team.aliens.dms.android.core.jwt.store

import team.aliens.dms.android.core.datastore.util.transform
import team.aliens.dms.android.core.jwt.AccessToken
import team.aliens.dms.android.core.jwt.AccessTokenExpiration
import team.aliens.dms.android.core.jwt.RefreshToken
import team.aliens.dms.android.core.jwt.RefreshTokenExpiration
import team.aliens.dms.android.core.jwt.datastore.JwtDataStoreDataSource
import javax.inject.Inject

internal class JwtStoreImpl @Inject constructor(
    private val jwtDataStoreDataSource: JwtDataStoreDataSource,
) : JwtStore() {
    override fun loadAccessToken(): AccessToken = jwtDataStoreDataSource.loadAccessToken()

    override suspend fun storeAccessToken(token: AccessToken) =
        transform { jwtDataStoreDataSource.storeAccessToken(token) }

    override fun loadAccessTokenExpiration(): AccessTokenExpiration =
        jwtDataStoreDataSource.loadAccessTokenExpiration()

    override suspend fun storeAccessTokenExpiration(expiration: AccessTokenExpiration) =
        transform { jwtDataStoreDataSource.storeAccessTokenExpiration(expiration) }

    override fun loadRefreshToken(): RefreshToken = jwtDataStoreDataSource.loadRefreshToken()

    override suspend fun storeRefreshToken(token: RefreshToken) =
        transform { jwtDataStoreDataSource.storeRefreshToken(token) }

    override fun loadRefreshTokenExpiration(): RefreshTokenExpiration =
        jwtDataStoreDataSource.loadRefreshTokenExpiration()

    override suspend fun storeRefreshTokenExpiration(expiration: RefreshTokenExpiration) =
        transform { jwtDataStoreDataSource.storeRefreshTokenExpiration(expiration) }
}
