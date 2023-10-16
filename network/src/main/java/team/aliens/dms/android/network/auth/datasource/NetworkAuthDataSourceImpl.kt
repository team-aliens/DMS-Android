package team.aliens.dms.android.network.auth.datasource

import team.aliens.dms.android.core.jwt.network.model.TokensResponse
import team.aliens.dms.android.core.network.util.sendHttpRequest
import team.aliens.dms.android.network.auth.apiservice.AuthApiService
import team.aliens.dms.android.network.auth.model.CheckIdExistsResponse
import team.aliens.dms.android.network.auth.model.EmailVerificationType
import team.aliens.dms.android.network.auth.model.SendEmailVerificationCodeRequest
import team.aliens.dms.android.network.auth.model.SignInRequest
import javax.inject.Inject

internal class NetworkAuthDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : NetworkAuthDataSource() {
    override suspend fun signIn(request: SignInRequest): TokensResponse =
        sendHttpRequest { authApiService.signIn(request) }

    override suspend fun sendEmailVerificationCode(request: SendEmailVerificationCodeRequest) =
        sendHttpRequest { authApiService.sendEmailVerificationCode(request) }

    override suspend fun checkEmailVerificationCode(
        email: String,
        authCode: String,
        type: EmailVerificationType,
    ) = sendHttpRequest { authApiService.checkEmailVerificationCode(email, authCode, type) }

    override suspend fun checkIdExists(accountId: String): CheckIdExistsResponse =
        sendHttpRequest { authApiService.checkIdExists(accountId) }
}
