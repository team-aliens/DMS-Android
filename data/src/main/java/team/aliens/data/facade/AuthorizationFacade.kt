package team.aliens.data.facade

import java.util.Date
import kotlinx.coroutines.runBlocking
import team.aliens.data.datasource.local.LocalAuthDataSource
import team.aliens.data.manager.TokenReissueManager
import team.aliens.data.util.toDate
import team.aliens.domain.model.auth.Token
import javax.inject.Inject

class AuthorizationFacade @Inject constructor(
    private val localAuthDataSource: LocalAuthDataSource,
    private val tokenReissueManager: TokenReissueManager,
) {
    val accessTokenAvailable: Boolean
        get() {
            val currentTime = Date()
            val accessTokenExpiredAt = this.accessTokenExpiredAt().toDate()

            return currentTime.before(accessTokenExpiredAt)
        }

    fun reissueAndSaveToken() {
        val refreshToken = this.refreshToken()
        val reissuedToken = tokenReissueManager.reissueToken(refreshToken)

        this.saveToken(
            token = reissuedToken,
        )
    }

    fun accessToken(): String {
        return runBlocking { localAuthDataSource.findAccessToken() }
    }

    fun accessTokenExpiredAt(): String {
        return runBlocking { localAuthDataSource.findAccessTokenExpiredAt() }
    }

    fun refreshToken(): String {
        return runBlocking { localAuthDataSource.findRefreshToken() }
    }

    fun refreshTokenExpiredAt(): String {
        return runBlocking { localAuthDataSource.findRefreshTokenExpiredAt() }
    }

    fun saveToken(token: Token) {
        runBlocking {
            localAuthDataSource.saveToken(
                token = token,
            )
        }
    }

    suspend fun clearToken() {
        runBlocking {
            localAuthDataSource.clearToken()
        }
    }
}
