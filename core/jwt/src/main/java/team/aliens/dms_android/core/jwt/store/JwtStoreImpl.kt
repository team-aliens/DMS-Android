package team.aliens.dms_android.core.jwt.store

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import team.aliens.dms_android.core.datastore.PreferencesDataStore
import team.aliens.dms_android.core.datastore.util.transform
import team.aliens.dms_android.core.jwt.AccessToken
import team.aliens.dms_android.core.jwt.AccessTokenExpiration
import team.aliens.dms_android.core.jwt.RefreshToken
import team.aliens.dms_android.core.jwt.RefreshTokenExpiration
import team.aliens.dms_android.core.jwt.exception.AccessTokenExpirationNotFoundException
import team.aliens.dms_android.core.jwt.exception.AccessTokenNotFoundException
import team.aliens.dms_android.core.jwt.exception.CannotStoreAccessTokenException
import team.aliens.dms_android.core.jwt.exception.CannotStoreAccessTokenExpirationException
import team.aliens.dms_android.core.jwt.exception.CannotStoreRefreshTokenException
import team.aliens.dms_android.core.jwt.exception.CannotStoreRefreshTokenExpirationException
import team.aliens.dms_android.core.jwt.exception.RefreshTokenExpirationNotFoundException
import team.aliens.dms_android.core.jwt.exception.RefreshTokenNotFoundException
import team.aliens.dms_android.shared.date.extension.toEpochMilli
import team.aliens.dms_android.shared.date.extension.toLocalDateTime
import javax.inject.Inject

internal class JwtStoreImpl @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore,
) : JwtStore() {
    override fun loadAccessToken(): AccessToken = runBlocking {
        preferencesDataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN] ?: throw AccessTokenNotFoundException()
        }.first()
    }

    override suspend fun storeAccessToken(token: AccessToken) {
        transform(
            job = {
                preferencesDataStore.edit { preferences ->
                    preferences[ACCESS_TOKEN] = token
                }
            },
            onFailure = { throw CannotStoreAccessTokenException() },
        )
    }

    override fun loadAccessTokenExpiration(): AccessTokenExpiration = runBlocking {
        preferencesDataStore.data.map { preferences ->
            val longValue = preferences[ACCESS_TOKEN_EXPIRATION]
                ?: throw AccessTokenExpirationNotFoundException()

            return@map longValue.toLocalDateTime()
        }.first()
    }

    override suspend fun storeAccessTokenExpiration(expiration: AccessTokenExpiration) {
        transform(
            job = {
                preferencesDataStore.edit { preferences ->
                    preferences[ACCESS_TOKEN_EXPIRATION] = expiration.toEpochMilli()
                }
            },
            onFailure = { throw CannotStoreAccessTokenExpirationException() },
        )
    }

    override fun loadRefreshToken(): RefreshToken = runBlocking {
        preferencesDataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN] ?: throw RefreshTokenNotFoundException()
        }.first()
    }

    override suspend fun storeRefreshToken(token: RefreshToken) {
        transform(
            job = {
                preferencesDataStore.edit { preferences ->
                    preferences[REFRESH_TOKEN] = token
                }
            },
            onFailure = { throw CannotStoreRefreshTokenException() },
        )
    }

    override fun loadRefreshTokenExpiration(): RefreshTokenExpiration = runBlocking {
        preferencesDataStore.data.map { preferences ->
            val longValue = preferences[REFRESH_TOKEN_EXPIRATION]
                ?: throw RefreshTokenExpirationNotFoundException()

            return@map longValue.toLocalDateTime()
        }.first()
    }

    override suspend fun storeRefreshTokenExpiration(expiration: RefreshTokenExpiration) {
        transform(
            job = {
                preferencesDataStore.edit { preferences ->
                    preferences[REFRESH_TOKEN_EXPIRATION] = expiration.toEpochMilli()
                }
            },
            onFailure = { throw CannotStoreRefreshTokenExpirationException() },
        )
    }

    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access-token")
        val ACCESS_TOKEN_EXPIRATION = longPreferencesKey("access-token-expiration")
        val REFRESH_TOKEN = stringPreferencesKey("refresh-token")
        val REFRESH_TOKEN_EXPIRATION = longPreferencesKey("refresh-token-expiration")
    }
}
