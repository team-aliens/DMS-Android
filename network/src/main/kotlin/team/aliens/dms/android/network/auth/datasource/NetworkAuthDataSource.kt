package team.aliens.dms.android.network.auth.datasource

import team.aliens.dms.android.network.auth.model.CheckIdExistsResponse
import team.aliens.dms.android.network.auth.model.SendEmailVerificationCodeRequest
import team.aliens.dms.android.network.auth.model.SignInRequest
import team.aliens.dms.android.network.auth.model.SignInResponse

abstract class NetworkAuthDataSource {

    abstract suspend fun signIn(request: SignInRequest): Result<SignInResponse>

    abstract suspend fun sendEmailVerificationCode(request: SendEmailVerificationCodeRequest): Result<Unit>

    abstract suspend fun checkEmailVerificationCode(
        email: String,
        code: String,
        type: String,
    ): Result<Unit>

    abstract suspend fun checkIdExists(accountId: String): Result<CheckIdExistsResponse>
}
