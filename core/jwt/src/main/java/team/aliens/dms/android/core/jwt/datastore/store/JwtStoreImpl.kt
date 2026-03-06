package team.aliens.dms.android.core.jwt.datastore.store

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import team.aliens.dms.android.core.datastore.JwtDataStore
import team.aliens.dms.android.core.datastore.PreferencesDataStore
import team.aliens.dms.android.core.datastore.util.transform
import team.aliens.dms.android.core.jwt.AccessToken
import team.aliens.dms.android.core.jwt.RefreshToken
import team.aliens.dms.android.core.jwt.Tokens
import team.aliens.dms.android.core.jwt.datastore.store.exception.AccessTokenExpirationNotFoundException
import team.aliens.dms.android.core.jwt.datastore.store.exception.AccessTokenNotFoundException
import team.aliens.dms.android.core.jwt.datastore.store.exception.CannotStoreTokensException
import team.aliens.dms.android.core.jwt.datastore.store.exception.RefreshTokenExpirationNotFoundException
import team.aliens.dms.android.core.jwt.datastore.store.exception.RefreshTokenNotFoundException
import team.aliens.dms.android.shared.date.toEpochMilli
import team.aliens.dms.android.shared.date.toLocalDateTime
import javax.inject.Inject

internal class JwtStoreImpl @Inject constructor(
    @JwtDataStore private val jwtDataStore: PreferencesDataStore,
) : JwtStore() {

    override fun loadTokens(): Tokens = runBlocking {
        jwtDataStore.data.map { preferences ->
            val accessTokenValue = preferences[ACCESS_TOKEN]
                ?: throw AccessTokenNotFoundException()
            val accessTokenExpiration = preferences[ACCESS_TOKEN_EXPIRATION]
                ?: throw AccessTokenExpirationNotFoundException()
            val refreshTokenValue = preferences[REFRESH_TOKEN]
                ?: throw RefreshTokenNotFoundException()
            val refreshTokenExpiration = preferences[REFRESH_TOKEN_EXPIRATION]
                ?: throw RefreshTokenExpirationNotFoundException()

            return@map Tokens(
                accessToken = AccessToken(
                    value = accessTokenValue,
                    expiration = accessTokenExpiration.toLocalDateTime(),
                ),
                refreshToken = RefreshToken(
                    value = refreshTokenValue,
                    expiration = refreshTokenExpiration.toLocalDateTime(),
                ),
            )
        }.first()
    }

    override suspend fun storeTokens(tokens: Tokens) {
        transform(onFailure = { throw CannotStoreTokensException() }) {
            jwtDataStore.edit { preferences ->
                val accessToken = tokens.accessToken
                val refreshToken = tokens.refreshToken
                preferences[ACCESS_TOKEN] = accessToken.value
                preferences[ACCESS_TOKEN_EXPIRATION] = accessToken.expiration.toEpochMilli()
                preferences[REFRESH_TOKEN] = refreshToken.value
                preferences[REFRESH_TOKEN_EXPIRATION] = refreshToken.expiration.toEpochMilli()
            }
        }
    }

    override suspend fun clearTokens() {
        transform { jwtDataStore.edit { preferences -> preferences.clear() } }
    }

    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access-token")
        val ACCESS_TOKEN_EXPIRATION = longPreferencesKey("access-token-expiration")
        val REFRESH_TOKEN = stringPreferencesKey("refresh-token")
        val REFRESH_TOKEN_EXPIRATION = longPreferencesKey("refresh-token-expiration")
    }
}
