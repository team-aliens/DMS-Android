package team.aliens.domain._repository

import team.aliens.domain._model._common.EmailVerificationType
import team.aliens.domain._model.auth.CheckIdExistsResult
import team.aliens.domain._model.auth.SendEmailVerificationCodeInput
import team.aliens.domain._model.auth.SignInInput
import team.aliens.domain._model.auth.TokenResult

interface AuthRepository {

    suspend fun signIn(
        input: SignInInput,
    ): TokenResult

    suspend fun sendEmailVerificationCode(
        input: SendEmailVerificationCodeInput,
    )

    suspend fun checkEmailVerificationCode(
        email: String,
        authCode: String,
        type: EmailVerificationType,
    )

    suspend fun reissueToken(): TokenResult

    suspend fun verifyEmail(
        accountId: String,
        email: String,
    )

    suspend fun checkIdExists(
        accountId: String,
    ): CheckIdExistsResult
}
