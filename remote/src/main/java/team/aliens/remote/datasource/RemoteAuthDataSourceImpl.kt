package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteAuthDataSource
import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model._common.EmailVerificationType
import team.aliens.domain._model.auth.CheckIdExistsInput
import team.aliens.domain._model.auth.CheckIdExistsOutput
import team.aliens.domain._model.auth.SendEmailVerificationCodeInput
import team.aliens.domain._model.auth.SignInInput
import team.aliens.remote.model._common.toDomain
import team.aliens.remote.model.auth.toData
import team.aliens.remote.model.auth.toDomain
import team.aliens.remote.service.AuthService
import team.aliens.remote.util.sendHttpRequest
import javax.inject.Inject

class RemoteAuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : RemoteAuthDataSource {

    override suspend fun signIn(
        input: SignInInput,
    ): AuthenticationOutput {
        return sendHttpRequest {
            authService.signIn(
                request = input.toData()
            )
        }.toDomain()
    }

    override suspend fun sendEmailVerificationCode(
        input: SendEmailVerificationCodeInput,
    ) {
        return sendHttpRequest {
            authService.sendEmailVerificationCode(
                request = input.toData()
            )
        }
    }

    override suspend fun checkEmailVerificationCode(
        email: String,
        authCode: String,
        type: EmailVerificationType,
    ) {
        return sendHttpRequest {
            authService.checkEmailVerificationCode(
                email = email,
                authCode = authCode,
                type = type.name,
            )
        }
    }

    // FIXME remove
    @Deprecated("can be removed in the future")
    override suspend fun reissueToken(): AuthenticationOutput {
        throw IllegalAccessException("stub!")
    }

    override suspend fun verifyEmail(
        accountId: String,
        email: String,
    ) {
        return sendHttpRequest {
            authService.verifyEmail(
                accountId = accountId,
                email = email,
            )
        }
    }

    override suspend fun checkIdExists(
        input: CheckIdExistsInput,
    ): CheckIdExistsOutput {
        return sendHttpRequest {
            authService.checkIdExists(
                accountId = input.accountId,
            )
        }.toDomain()
    }
}
