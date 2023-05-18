package team.aliens.remote.datasource

import team.aliens.data.datasource.remote.RemoteAuthDataSource
import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model._common.EmailVerificationType
import team.aliens.domain._model.auth.CheckIdExistsInput
import team.aliens.domain._model.auth.CheckIdExistsOutput
import team.aliens.domain._model.auth.SendEmailVerificationCodeInput
import team.aliens.domain._model.auth.SignInInput
import team.aliens.remote.apiservice.AuthApiService
import team.aliens.remote.model._common.toDomain
import team.aliens.remote.model.auth.toData
import team.aliens.remote.model.auth.toDomain
import team.aliens.remote.util.sendHttpRequest
import javax.inject.Inject

class RemoteAuthDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : RemoteAuthDataSource {

    override suspend fun signIn(
        input: SignInInput,
    ): AuthenticationOutput {
        return sendHttpRequest {
            authApiService.signIn(
                request = input.toData()
            )
        }.toDomain()
    }

    override suspend fun sendEmailVerificationCode(
        input: SendEmailVerificationCodeInput,
    ) {
        return sendHttpRequest {
            authApiService.sendEmailVerificationCode(
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
            authApiService.checkEmailVerificationCode(
                email = email,
                authCode = authCode,
                type = type.name,
            )
        }
    }

    override suspend fun reissueToken(
        refreshToken: String,
    ): AuthenticationOutput {
        return sendHttpRequest {
            authApiService.reissueToken(
                refreshToken = refreshToken,
            )
        }.toDomain()
    }

    override suspend fun verifyEmail(
        accountId: String,
        email: String,
    ) {
        return sendHttpRequest {
            authApiService.verifyEmail(
                accountId = accountId,
                email = email,
            )
        }
    }

    override suspend fun checkIdExists(
        input: CheckIdExistsInput,
    ): CheckIdExistsOutput {
        return sendHttpRequest {
            authApiService.checkIdExists(
                accountId = input.accountId,
            )
        }.toDomain()
    }
}
