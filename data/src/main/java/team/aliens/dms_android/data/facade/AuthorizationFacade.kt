package team.aliens.dms_android.data.facade

import java.util.Date
import kotlinx.coroutines.runBlocking
import team.aliens.dms_android.data.datasource.local.LocalAuthDataSource
import team.aliens.dms_android.data.datasource.local.LocalSchoolDataSource
import team.aliens.dms_android.data.manager.TokenReissueManager
import team.aliens.dms_android.data.util.toDate
import team.aliens.dms_android.domain.model._common.AuthenticationOutput
import team.aliens.dms_android.domain.model._common.toModel
import team.aliens.dms_android.domain.model.auth.Token
import team.aliens.dms_android.domain.model.student.Features
import javax.inject.Inject

class AuthorizationFacade @Inject constructor(
    private val localAuthDataSource: LocalAuthDataSource,
    private val localSchoolDataSource: LocalSchoolDataSource,
    private val tokenReissueManager: TokenReissueManager,
) {
    val accessTokenAvailable: Boolean
        get() = currentTime.before(accessTokenExpiredAt)

    val refreshTokenAvailable: Boolean
        get() = currentTime.before(refreshTokenExpiredAt)

    private val currentTime: Date
        get() = Date()

    val token: Token
        get() = runBlocking { localAuthDataSource.findToken() }

    val accessToken: String
        get() = runBlocking { localAuthDataSource.findAccessToken() }

    val accessTokenExpiredAt: Date
        get() = runBlocking { localAuthDataSource.findAccessTokenExpiredAt() }.toDate()

    val refreshToken: String
        get() = runBlocking { localAuthDataSource.findRefreshToken() }

    val refreshTokenExpiredAt: Date
        get() = runBlocking { localAuthDataSource.findRefreshTokenExpiredAt() }.toDate()


    fun reissueToken(): AuthenticationOutput {
        return tokenReissueManager.reissueToken(refreshToken)
    }

    fun saveFeatures(features: Features) {
        runBlocking { localSchoolDataSource.saveFeatures(features) }
    }

    fun reissueAndSaveTokenAndFeatures(): Token {
        val authenticationOutput = reissueToken()
        val token = Token(
            accessToken = authenticationOutput.accessToken,
            accessTokenExpiredAt = authenticationOutput.accessTokenExpiredAt,
            refreshToken = authenticationOutput.refreshToken,
            refreshTokenExpiredAt = authenticationOutput.refreshTokenExpiredAt,
        )
        saveFeatures(authenticationOutput.features.toModel())
        saveToken(token)
        return token
    }

    fun accessTokenOrReissue(): String {
        return if (accessTokenAvailable) {
            this.accessToken
        } else {
            reissueAndSaveTokenAndFeatures().accessToken
        }
    }

    fun saveToken(token: Token) {
        runBlocking {
            localAuthDataSource.saveToken(
                token = token,
            )
        }
    }

    fun clearToken() {
        runBlocking {
            localAuthDataSource.clearToken()
        }
    }
}
