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
        get() = currentTime.before(accessTokenExpiredAt)

    val refreshTokenAvailable: Boolean
        get() = currentTime.before(refreshTokenExpiredAt)

    private val currentTime: Date
        get() = Date()

    val token: Token
        get() = runBlocking {
            localAuthDataSource.findToken()
        }

    val accessToken: String
        get() = runBlocking {
            localAuthDataSource.findAccessToken()
        }

    val accessTokenExpiredAt: Date
        get() = runBlocking {
            localAuthDataSource.findAccessTokenExpiredAt()
        }.toDate()

    val refreshToken: String
        get() = runBlocking {
            localAuthDataSource.findRefreshToken()
        }

    val refreshTokenExpiredAt: Date
        get() = runBlocking {
            localAuthDataSource.findRefreshTokenExpiredAt()
        }.toDate()


    fun reissueToken(): Token {
        return tokenReissueManager.reissueToken(refreshToken)
    }

    fun reissueAndSaveToken(): Token {
        return reissueToken().also {
            saveToken(it)
        }
    }

    fun fetchAccessTokenOrElseReissue(): String {
        return if (accessTokenAvailable) {
            this.accessToken
        } else {
            reissueAndSaveToken().accessToken
        }
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
