package team.aliens.data._repository

import team.aliens.data._datasource.local.LocalAuthDataSource
import team.aliens.data._datasource.remote.RemoteAuthDataSource
import team.aliens.domain._exception.CommonException
import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model.auth.CheckEmailVerificationCodeInput
import team.aliens.domain._model.auth.CheckIdExistsInput
import team.aliens.domain._model.auth.CheckIdExistsOutput
import team.aliens.domain._model.auth.SendEmailVerificationCodeInput
import team.aliens.domain._model.auth.SignInInput
import team.aliens.domain._model.auth.Token
import team.aliens.domain._repository.AuthRepository
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

    override suspend fun autoSignIn() {
        val autoSignInEnabled = this.findAutoSignInOption()

        if (autoSignInEnabled) {
            this.reissueToken()
            return
        }

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

    override suspend fun findAccessTokenExpiredAt(): String {
        return localAuthDataSource.findAccessTokenExpiredAt()
    }

    override suspend fun findRefreshToken(): String {
        return localAuthDataSource.findRefreshToken()
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
