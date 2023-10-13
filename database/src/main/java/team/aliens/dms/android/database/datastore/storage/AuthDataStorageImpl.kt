package team.aliens.dms.android.database.datastore.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import java.util.Date
import kotlinx.coroutines.flow.first
import team.aliens.dms.android.domain.exception.LocalException
import team.aliens.dms.android.database.datastore.common.DataStoreProperty.Key.Auth.AccessToken
import team.aliens.dms.android.database.datastore.common.DataStoreProperty.Key.Auth.AccessTokenExpiredAt
import team.aliens.dms.android.database.datastore.common.DataStoreProperty.Key.Auth.AutoSignIn
import team.aliens.dms.android.database.datastore.common.DataStoreProperty.Key.Auth.RefreshToken
import team.aliens.dms.android.database.datastore.common.DataStoreProperty.Key.Auth.RefreshTokenExpiredAt
import team.aliens.dms.android.database.util.toDate
import team.aliens.dms.android.domain.model.auth.Token
import javax.inject.Inject

// TODO: remove
@Deprecated("No usage")
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
        return dataStore.data.first()[AccessToken] ?: throw LocalException.AccessTokenNotFound
    }

    override suspend fun checkAccessTokenAvailable(): Boolean {
        return try {
            val currentTime = Date()
            val accessTokenExpiredAt = this.findAccessTokenExpiredAt().toDate()
            currentTime.before(accessTokenExpiredAt)
        } catch (e: LocalException.AccessTokenExpiredAtNotFound) {
            false
        }
    }

    override suspend fun findAccessTokenExpiredAt(): String {
        return dataStore.data.first()[AccessTokenExpiredAt]
            ?: throw LocalException.AccessTokenExpiredAtNotFound
    }

    override suspend fun findRefreshToken(): String {
        return dataStore.data.first()[RefreshToken] ?: throw LocalException.RefreshTokenNotFound
    }

    override suspend fun checkRefreshTokenAvailable(): Boolean {
        return try {
            val currentTime = Date()
            val refreshTokenExpiredAt = this.findRefreshTokenExpiredAt().toDate()
            currentTime.before(refreshTokenExpiredAt)
        } catch (e: LocalException.RefreshTokenExpiredAtNotFound) {
            false
        }
    }

    override suspend fun findRefreshTokenExpiredAt(): String {
        return dataStore.data.first()[RefreshTokenExpiredAt]
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

    override suspend fun signOut() {
        dataStore.edit { it.clear() }
    }

    override suspend fun findAutoSignInOption(): Boolean {
        return dataStore.data.first()[AutoSignIn] ?: false
    }

    override suspend fun updateAutoSignInOption(
        autoSignIn: Boolean,
    ) {
        dataStore.edit {
            it[AutoSignIn] = autoSignIn
        }
    }
}
