package team.aliens.local.datasource

import team.aliens.data.datasource.local.LocalAuthDataSource
import team.aliens.domain.model.auth.Token
import team.aliens.local.datastore.storage.AuthDataStorage
import javax.inject.Inject

class LocalAuthDataSourceImpl @Inject constructor(
    private val authDataStorage: AuthDataStorage,
) : LocalAuthDataSource {

    override suspend fun findToken(): Token {
        return authDataStorage.findToken()
    }

    override suspend fun findAccessToken(): String {
        return authDataStorage.findAccessToken()
    }

    override suspend fun findAccessTokenExpiredAt(): String {
        return authDataStorage.findAccessTokenExpiredAt()
    }

    override suspend fun findRefreshToken(): String {
        return authDataStorage.findRefreshToken()
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

    override suspend fun signOut() {
        authDataStorage.signOut()
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
