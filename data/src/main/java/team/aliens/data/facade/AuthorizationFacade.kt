package team.aliens.data.facade

import team.aliens.data.datasource.local.LocalAuthDataSource
import team.aliens.domain.model.auth.Token
import javax.inject.Inject

class AuthorizationFacade @Inject constructor(
    private val localAuthDataSource: LocalAuthDataSource,
) {

    suspend fun accessToken(): String {
        return localAuthDataSource.findAccessToken()
    }

    suspend fun accessTokenExpiredAt(): String {
        return localAuthDataSource.findAccessTokenExpiredAt()
    }

    suspend fun refreshToken(): String {
        return localAuthDataSource.findRefreshToken()
    }

    suspend fun refreshTokenExpiredAt(): String {
        return localAuthDataSource.findRefreshTokenExpiredAt()
    }

    suspend fun saveToken(
        token: Token,
    ) {
        localAuthDataSource.saveToken(
            token = token,
        )
    }

    suspend fun clearToken() {
        localAuthDataSource.clearToken()
    }
}
