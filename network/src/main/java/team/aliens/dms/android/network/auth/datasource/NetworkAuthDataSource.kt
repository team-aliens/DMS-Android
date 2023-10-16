package team.aliens.dms.android.network.auth.datasource

import team.aliens.dms.android.core.jwt.network.model.TokensResponse
import team.aliens.dms.android.network.auth.model.CheckIdExistsResponse
import team.aliens.dms.android.network.auth.model.EmailVerificationType
import team.aliens.dms.android.network.auth.model.SendEmailVerificationCodeRequest
import team.aliens.dms.android.network.auth.model.SignInRequest

abstract class NetworkAuthDataSource {

    abstract suspend fun signIn(request: SignInRequest): TokensResponse

    abstract suspend fun sendEmailVerificationCode(request: SendEmailVerificationCodeRequest)

    abstract suspend fun checkEmailVerificationCode(
        email: String,
        authCode: String,
        type: EmailVerificationType,
    )

    abstract suspend fun checkIdExists(accountId: String): CheckIdExistsResponse
}
