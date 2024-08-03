package team.aliens.dms.android.data.auth.repository

import team.aliens.dms.android.data.auth.model.EmailVerificationType
import team.aliens.dms.android.data.auth.model.HashedEmail

abstract class AuthRepository {

    abstract suspend fun signIn(
        accountId: String,
        password: String,
        deviceToken: String,
        autoSignIn: Boolean = true,
    )

    abstract suspend fun sendEmailVerificationCode(
        email: String,
        type: EmailVerificationType,
    )

    abstract suspend fun checkEmailVerificationCode(
        email: String,
        code: String,
        type: EmailVerificationType,
    )

    abstract suspend fun checkIdExists(accountId: String): HashedEmail

    abstract suspend fun signOut()
}
