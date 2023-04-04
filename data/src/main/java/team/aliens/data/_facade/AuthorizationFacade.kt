package team.aliens.data._facade

import team.aliens.data._datasource.local.LocalAuthDataSource
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
}
