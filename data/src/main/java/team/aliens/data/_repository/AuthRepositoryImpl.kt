package team.aliens.data._repository

import team.aliens.data._datasource.local.LocalAuthDataSource
import team.aliens.data._datasource.remote.RemoteAuthDataSource
import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model._common.EmailVerificationType
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
        )
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
        email: String,
        authCode: String,
        type: EmailVerificationType,
    ) {
        return remoteAuthDataSource.checkEmailVerificationCode(
            email = email,
            authCode = authCode,
            type = type,
        )
    }

    override suspend fun reissueToken(): AuthenticationOutput {
        return remoteAuthDataSource.reissueToken()
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
        accountId: String,
    ): CheckIdExistsOutput {
        return checkIdExists(
            accountId = accountId,
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
