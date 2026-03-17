package team.aliens.dms.android.data.auth.repository

import team.aliens.dms.android.data.auth.model.EmailVerificationType
import team.aliens.dms.android.data.auth.model.HashedEmail
import team.aliens.dms.android.network.auth.model.CheckIdExistsResponse
import team.aliens.dms.android.network.auth.model.SignInResponse

abstract class AuthRepository {

    abstract suspend fun signIn(
        accountId: String,
        password: String,
        deviceToken: String,
    ): Result<SignInResponse>

    abstract suspend fun sendEmailVerificationCode(
        email: String,
        type: EmailVerificationType,
    ): Result<Unit>

    abstract suspend fun checkEmailVerificationCode(
        email: String,
        code: String,
        type: EmailVerificationType,
    ): Result<Unit>

    abstract suspend fun checkIdExists(accountId: String): Result<CheckIdExistsResponse>

    abstract suspend fun signOut(): Result<Unit>
}
