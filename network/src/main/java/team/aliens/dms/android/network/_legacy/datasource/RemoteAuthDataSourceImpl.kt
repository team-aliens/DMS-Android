package team.aliens.dms.android.network._legacy.datasource

import team.aliens.dms.android.data.datasource.remote.RemoteAuthDataSource
import team.aliens.dms.android.network._legacy.apiservice.AuthApiService
import team.aliens.dms.android.network.model._common.toDomain
import team.aliens.dms.android.network.model.auth.toData
import team.aliens.dms.android.network.model.auth.toDomain
import team.aliens.dms.android.network.util.sendHttpRequest
import team.aliens.dms.android.domain.exception.AuthException
import team.aliens.dms.android.domain.model._common.AuthenticationOutput
import team.aliens.dms.android.domain.model._common.EmailVerificationType
import team.aliens.dms.android.domain.model.auth.CheckIdExistsInput
import team.aliens.dms.android.domain.model.auth.CheckIdExistsOutput
import team.aliens.dms.android.domain.model.auth.SendEmailVerificationCodeInput
import team.aliens.dms.android.domain.model.auth.SignInInput
import javax.inject.Inject

class RemoteAuthDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : RemoteAuthDataSource {

    override suspend fun signIn(
        input: SignInInput,
    ): AuthenticationOutput {
        return sendHttpRequest(
            onUnauthorized = { throw AuthException.PasswordMismatch },
            onNotFound = { throw AuthException.UserNotFound },
        ) {
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
