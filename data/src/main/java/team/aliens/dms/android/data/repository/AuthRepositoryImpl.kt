package team.aliens.dms.android.data.repository

import team.aliens.dms.android.data.datasource.local.LocalAuthDataSource
import team.aliens.dms.android.data.datasource.remote.RemoteAuthDataSource
import team.aliens.dms.android.domain.exception.CommonException
import team.aliens.dms.android.domain.model._common.AuthenticationOutput
import team.aliens.dms.android.domain.model.auth.CheckEmailVerificationCodeInput
import team.aliens.dms.android.domain.model.auth.CheckIdExistsInput
import team.aliens.dms.android.domain.model.auth.CheckIdExistsOutput
import team.aliens.dms.android.domain.model.auth.SendEmailVerificationCodeInput
import team.aliens.dms.android.domain.model.auth.SignInInput
import team.aliens.dms.android.domain.model.auth.Token
import team.aliens.dms.android.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localAuthDataSource: LocalAuthDataSource,
    private val remoteAuthDataSource: RemoteAuthDataSource,
) : AuthRepository {

    override suspend fun signIn(
        input: SignInInput,
    ): AuthenticationOutput {
        return remoteAuthDataSource.signIn(
            input = input
        ).also {
            this.saveToken(
                Token(
                    accessToken = it.accessToken,
                    accessTokenExpiredAt = it.accessTokenExpiredAt,
                    refreshToken = it.refreshToken,
                    refreshTokenExpiredAt = it.refreshTokenExpiredAt,
                ),
            )
            this.updateAutoSignInOption(
                autoSignIn = input.autoSignIn,
            )
        }
    }

    override suspend fun autoSignIn(): AuthenticationOutput {
        val autoSignInEnabled = this.findAutoSignInOption()
        if (autoSignInEnabled) return this.reissueToken()
        throw CommonException.SignInRequired
    }

    override suspend fun findAutoSignInOption(): Boolean {
        return localAuthDataSource.findAutoSignInOption()
    }

    override suspend fun updateAutoSignInOption(
        autoSignIn: Boolean,
    ) {
        return localAuthDataSource.updateAutoSignInOption(
            autoSignIn = autoSignIn,
        )
    }

    override suspend fun sendEmailVerificationCode(
        input: SendEmailVerificationCodeInput,
    ) {
        return remoteAuthDataSource.sendEmailVerificationCode(
            input = input,
        )
    }

    override suspend fun checkEmailVerificationCode(
        input: CheckEmailVerificationCodeInput,
    ) {
        return remoteAuthDataSource.checkEmailVerificationCode(
            email = input.email,
            authCode = input.authCode,
            type = input.type,
        )
    }

    override suspend fun reissueToken(): AuthenticationOutput {
        val refreshToken = this.findRefreshToken()

        return remoteAuthDataSource.reissueToken(
            refreshToken = refreshToken,
        )
    }

    override suspend fun verifyEmail(
        accountId: String,
        email: String,
    ) {
        return remoteAuthDataSource.verifyEmail(
            accountId = accountId,
            email = email,
        )
    }

    override suspend fun checkIdExists(
        input: CheckIdExistsInput,
    ): CheckIdExistsOutput {
        return remoteAuthDataSource.checkIdExists(
            input = input,
        )
    }

    override suspend fun findToken(): Token {
        return localAuthDataSource.findToken()
    }

    override suspend fun findAccessToken(): String {
        return localAuthDataSource.findAccessToken()
    }

    override suspend fun checkAccessTokenAvailable(): Boolean {
        return localAuthDataSource.checkAccessTokenAvailable()
    }

    override suspend fun findAccessTokenExpiredAt(): String {
        return localAuthDataSource.findAccessTokenExpiredAt()
    }

    override suspend fun findRefreshToken(): String {
        return localAuthDataSource.findRefreshToken()
    }

    override suspend fun checkRefreshTokenAvailable(): Boolean {
        return localAuthDataSource.checkRefreshTokenAvailable()
    }

    override suspend fun findRefreshTokenExpiredAt(): String {
        return localAuthDataSource.findRefreshTokenExpiredAt()
    }

    override suspend fun saveToken(
        token: Token,
    ) {
        localAuthDataSource.saveToken(
            token = token,
        )
    }

    override suspend fun clearToken() {
        localAuthDataSource.clearToken()
    }
}
