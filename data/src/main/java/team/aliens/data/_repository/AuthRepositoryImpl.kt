package team.aliens.data._repository

import team.aliens.data._datasource.remote.RemoteAuthDataSource
import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model._common.EmailVerificationType
import team.aliens.domain._model.auth.CheckIdExistsOutput
import team.aliens.domain._model.auth.SendEmailVerificationCodeInput
import team.aliens.domain._model.auth.SignInInput
import team.aliens.domain._repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteAuthDataSource: RemoteAuthDataSource,
) : AuthRepository {

    override suspend fun signIn(
        input: SignInInput,
    ): AuthenticationOutput {
        return remoteAuthDataSource.signIn(
            input = input
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
}
