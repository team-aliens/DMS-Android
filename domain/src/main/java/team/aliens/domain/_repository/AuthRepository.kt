package team.aliens.domain._repository

import team.aliens.domain._model._common.EmailVerificationType
import team.aliens.domain._model.auth.CheckIdExistsOutput
import team.aliens.domain._model.auth.SendEmailVerificationCodeInput
import team.aliens.domain._model.auth.SignInInput
import team.aliens.domain._model._common.AuthenticationOutput

interface AuthRepository {

    suspend fun signIn(
        input: SignInInput,
    ): AuthenticationOutput

    suspend fun sendEmailVerificationCode(
        input: SendEmailVerificationCodeInput,
    )

    suspend fun checkEmailVerificationCode(
        email: String,
        authCode: String,
        type: EmailVerificationType,
    )

    suspend fun reissueToken(): AuthenticationOutput

    suspend fun verifyEmail(
        accountId: String,
        email: String,
    )

    suspend fun checkIdExists(
        accountId: String,
    ): CheckIdExistsOutput
}
