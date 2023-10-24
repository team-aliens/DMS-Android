package team.aliens.dms.android.core.jwt.datastore.store

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import team.aliens.dms.android.core.datastore.PreferencesDataStore
import team.aliens.dms.android.core.datastore.util.transform
import team.aliens.dms.android.core.jwt.Tokens
import team.aliens.dms.android.core.jwt.exception.AccessTokenExpirationNotFoundException
import team.aliens.dms.android.core.jwt.exception.AccessTokenNotFoundException
import team.aliens.dms.android.core.jwt.exception.CannotStoreTokensException
import team.aliens.dms.android.core.jwt.exception.RefreshTokenExpirationNotFoundException
import team.aliens.dms.android.core.jwt.exception.RefreshTokenNotFoundException
import team.aliens.dms.android.shared.date.toEpochMilli
import team.aliens.dms.android.shared.date.toLocalDateTime
import javax.inject.Inject

internal class JwtStoreImpl @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore,
) : JwtStore() {

    override fun loadTokens(): Tokens = runBlocking {
        preferencesDataStore.data.map { preferences ->
            val accessToken = preferences[ACCESS_TOKEN] ?: throw AccessTokenNotFoundException()
            val accessTokenExpiration = preferences[ACCESS_TOKEN_EXPIRATION]
                ?: throw AccessTokenExpirationNotFoundException()
            val refreshToken = preferences[REFRESH_TOKEN] ?: throw RefreshTokenNotFoundException()
            val refreshTokenExpiration = preferences[REFRESH_TOKEN_EXPIRATION]
                ?: throw RefreshTokenExpirationNotFoundException()

            return@map Tokens(
                accessToken = accessToken,
                accessTokenExpiration = accessTokenExpiration.toLocalDateTime(),
                refreshToken = refreshToken,
                refreshTokenExpiration = refreshTokenExpiration.toLocalDateTime(),
            )
        }.first()
    }

    override suspend fun storeTokens(tokens: Tokens) {
        transform(
            onFailure = { throw CannotStoreTokensException() },
        ) {
            preferencesDataStore.edit { preferences ->
                preferences[ACCESS_TOKEN] = tokens.accessToken
                preferences[ACCESS_TOKEN_EXPIRATION] = tokens.accessTokenExpiration.toEpochMilli()
                preferences[REFRESH_TOKEN] = tokens.refreshToken
                preferences[REFRESH_TOKEN_EXPIRATION] = tokens.refreshTokenExpiration.toEpochMilli()
            }
        }
    }

    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access-token")
        val ACCESS_TOKEN_EXPIRATION = longPreferencesKey("access-token-expiration")
        val REFRESH_TOKEN = stringPreferencesKey("refresh-token")
        val REFRESH_TOKEN_EXPIRATION = longPreferencesKey("refresh-token-expiration")
    }
}
