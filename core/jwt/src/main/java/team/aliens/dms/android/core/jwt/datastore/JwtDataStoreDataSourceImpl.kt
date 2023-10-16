package team.aliens.dms.android.core.jwt.datastore

import team.aliens.dms.android.core.datastore.util.transform
import team.aliens.dms.android.core.jwt.AccessToken
import team.aliens.dms.android.core.jwt.AccessTokenExpiration
import team.aliens.dms.android.core.jwt.RefreshToken
import team.aliens.dms.android.core.jwt.RefreshTokenExpiration
import team.aliens.dms.android.core.jwt.Tokens
import team.aliens.dms.android.core.jwt.datastore.store.JwtStore
import javax.inject.Inject

internal class JwtDataStoreDataSourceImpl @Inject constructor(
    private val jwtStore: JwtStore,
) : JwtDataStoreDataSource() {

    override fun loadTokens(): Tokens {

    }

    override fun storeTokens(tokens: Tokens) {
        TODO("Not yet implemented")
    }

    override fun loadAccessToken(): AccessToken = jwtStore.loadAccessToken()

    override suspend fun storeAccessToken(token: AccessToken) =
        transform { jwtStore.storeAccessToken(token) }

    override fun loadAccessTokenExpiration(): AccessTokenExpiration =
        jwtStore.loadAccessTokenExpiration()

    override suspend fun storeAccessTokenExpiration(expiration: AccessTokenExpiration) =
        transform { jwtStore.storeAccessTokenExpiration(expiration) }

    override fun loadRefreshToken(): RefreshToken = jwtStore.loadRefreshToken()

    override suspend fun storeRefreshToken(token: RefreshToken) =
        transform { jwtStore.storeRefreshToken(token) }

    override fun loadRefreshTokenExpiration(): RefreshTokenExpiration =
        jwtStore.loadRefreshTokenExpiration()

    override suspend fun storeRefreshTokenExpiration(expiration: RefreshTokenExpiration) =
        transform { jwtStore.storeRefreshTokenExpiration(expiration) }
}
