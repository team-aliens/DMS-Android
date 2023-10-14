package team.aliens.dms.android.database._datasource

import team.aliens.dms.android.data.datasource.local.LocalAuthDataSource
import team.aliens.dms.android.domain.model.auth.Token
import team.aliens.dms.android.database.datastore.storage.AuthDataStorage
import javax.inject.Inject

// TODO: remove
@Deprecated("No usage")
class LocalAuthDataSourceImpl @Inject constructor(
    private val authDataStorage: AuthDataStorage,
) : LocalAuthDataSource {

    override suspend fun findToken(): Token {
        return authDataStorage.findToken()
    }

    override suspend fun findAccessToken(): String {
        return authDataStorage.findAccessToken()
    }

    override suspend fun checkAccessTokenAvailable(): Boolean {
        return authDataStorage.checkAccessTokenAvailable()
    }

    override suspend fun findAccessTokenExpiredAt(): String {
        return authDataStorage.findAccessTokenExpiredAt()
    }

    override suspend fun findRefreshToken(): String {
        return authDataStorage.findRefreshToken()
    }

    override suspend fun checkRefreshTokenAvailable(): Boolean {
        return authDataStorage.checkRefreshTokenAvailable()
    }

    override suspend fun findRefreshTokenExpiredAt(): String {
        return authDataStorage.findRefreshTokenExpiredAt()
    }

    override suspend fun saveToken(
        token: Token,
    ) {
        authDataStorage.saveToken(
            token = token,
        )
    }

    override suspend fun clearToken() {
        authDataStorage.clearToken()
    }

    override suspend fun findAutoSignInOption(): Boolean {
        return authDataStorage.findAutoSignInOption()
    }

    override suspend fun updateAutoSignInOption(
        autoSignIn: Boolean,
    ) {
        authDataStorage.updateAutoSignInOption(
            autoSignIn = autoSignIn,
        )
    }
}
