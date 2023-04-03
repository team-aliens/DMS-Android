package team.aliens.domain._repository

import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model._common.EmailVerificationType
import team.aliens.domain._model.auth.CheckIdExistsOutput
import team.aliens.domain._model.auth.SendEmailVerificationCodeInput
import team.aliens.domain._model.auth.SignInInput
import team.aliens.domain._model.auth.Token

interface AuthRepository {

    suspend fun signIn(
        input: SignInInput,
    ): AuthenticationOutput

    suspend fun findAutoSignInOption(): Boolean

    suspend fun updateAutoSignInOption(
        autoSignIn: Boolean,
    )

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

    suspend fun findToken(): Token

    suspend fun findAccessToken(): String

    suspend fun findAccessTokenExpiredAt(): String

    suspend fun findRefreshToken(): String

    suspend fun findRefreshTokenExpiredAt(): String

    suspend fun saveToken(
        token: Token,
    )

    suspend fun clearToken()
}
