package team.aliens.dms.android.data.auth.repository

import team.aliens.dms.android.data.auth.model.CheckIdExistsOutput
import team.aliens.dms.android.shared.model.EmailVerificationType

abstract class AuthRepository {

    abstract suspend fun signIn(
        accountId: String,
        password: String,
    )

    abstract suspend fun sendEmailVerificationCode(
        email: String,
        type: EmailVerificationType,
    )

    abstract suspend fun examineEmailVerificationCode(
        email: String,
        code: String,
        type: EmailVerificationType,
    )

    abstract suspend fun checkIdExists(accountId: String): CheckIdExistsOutput
}
