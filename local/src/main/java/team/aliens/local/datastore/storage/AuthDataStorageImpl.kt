package team.aliens.local.datastore.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.last
import team.aliens.domain._exception.LocalException
import team.aliens.domain._model.auth.Token
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.AccessToken
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.AccessTokenExpiredAt
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.AutoSignIn
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.RefreshToken
import team.aliens.local.datastore.common.DataStoreProperty.Key.Auth.RefreshTokenExpiredAt
import javax.inject.Inject

class AuthDataStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : AuthDataStorage {

    override suspend fun findToken(): Token {

        val fetchedAccessToken = this.findAccessToken()
        val fetchedAccessTokenExpiredAt = this.findAccessTokenExpiredAt()
        val fetchedRefreshToken = this.findRefreshToken()
        val fetchedRefreshTokenExpiredAt = this.findRefreshTokenExpiredAt()

        return Token(
            accessToken = fetchedAccessToken,
            accessTokenExpiredAt = fetchedAccessTokenExpiredAt,
            refreshToken = fetchedRefreshToken,
            refreshTokenExpiredAt = fetchedRefreshTokenExpiredAt,
        )
    }

    override suspend fun findAccessToken(): String {
        return dataStore.data.last()[AccessToken] ?: throw LocalException.AccessTokenNotFound
    }

    override suspend fun findAccessTokenExpiredAt(): String {
        return dataStore.data.last()[AccessTokenExpiredAt]
            ?: throw LocalException.AccessTokenExpiredNotFound
    }

    override suspend fun findRefreshToken(): String {
        return dataStore.data.last()[RefreshToken] ?: throw LocalException.RefreshTokenNotFound
    }

    override suspend fun findRefreshTokenExpiredAt(): String {
        return dataStore.data.last()[RefreshTokenExpiredAt]
            ?: throw LocalException.RefreshTokenExpiredAtNotFound
    }

    override suspend fun saveToken(
        token: Token,
    ) {
        dataStore.edit {
            with(token) {
                it[AccessToken] = this.accessToken
                it[AccessTokenExpiredAt] = this.accessTokenExpiredAt
                it[RefreshToken] = this.refreshToken
                it[RefreshTokenExpiredAt] = this.refreshTokenExpiredAt
            }
        }
    }

    override suspend fun clearToken() {
        dataStore.edit {
            it.clear()
        }
    }

    override suspend fun findAutoSignInOption(): Boolean {
        return dataStore.data.last()[AutoSignIn] ?: false
    }

    override suspend fun updateAutoSignInOption(
        autoSignIn: Boolean,
    ) {
        dataStore.edit {
            it[AutoSignIn] = autoSignIn
        }
    }
}
