package team.aliens.dms.android.network.auth.datasource
import team.aliens.dms.android.network.auth.apiservice.AuthApiService
import team.aliens.dms.android.network.auth.model.CheckIdExistsResponse
import team.aliens.dms.android.network.auth.model.SendEmailVerificationCodeRequest
import team.aliens.dms.android.network.auth.model.SignInRequest
import team.aliens.dms.android.network.auth.model.SignInResponse
import javax.inject.Inject

internal class NetworkAuthDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : NetworkAuthDataSource() {
    override suspend fun signIn(request: SignInRequest): Result<SignInResponse> = runCatching {
        authApiService.signIn(request)
    }

    override suspend fun sendEmailVerificationCode(request: SendEmailVerificationCodeRequest): Result<Unit> = runCatching {
        authApiService.sendEmailVerificationCode(request)
    }

    override suspend fun checkEmailVerificationCode(
        email: String,
        code: String,
        type: String,
    ) = runCatching {
        authApiService.checkEmailVerificationCode(email, code, type)
    }

    override suspend fun checkIdExists(accountId: String): Result<CheckIdExistsResponse> = runCatching {
        authApiService.checkIdExists(accountId)
    }
}
