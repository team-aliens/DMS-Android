package team.aliens.dms.android.domain.repository

import team.aliens.dms.android.domain.model._common.AuthenticationOutput
import team.aliens.dms.android.domain.model.auth.CheckEmailVerificationCodeInput
import team.aliens.dms.android.domain.model.auth.CheckIdExistsInput
import team.aliens.dms.android.domain.model.auth.CheckIdExistsOutput
import team.aliens.dms.android.domain.model.auth.SendEmailVerificationCodeInput
import team.aliens.dms.android.domain.model.auth.SignInInput
import team.aliens.dms.android.domain.model.auth.Token

interface AuthRepository {

    suspend fun signIn(
        input: SignInInput,
    ): AuthenticationOutput

    suspend fun autoSignIn(): AuthenticationOutput

    suspend fun findAutoSignInOption(): Boolean

    suspend fun updateAutoSignInOption(
        autoSignIn: Boolean,
    )

    suspend fun sendEmailVerificationCode(
        input: SendEmailVerificationCodeInput,
    )

    suspend fun checkEmailVerificationCode(
        input: CheckEmailVerificationCodeInput,
    )

    suspend fun reissueToken(): AuthenticationOutput

    suspend fun verifyEmail(
        accountId: String,
        email: String,
    )

    suspend fun checkIdExists(
        input: CheckIdExistsInput,
    ): CheckIdExistsOutput

    suspend fun findToken(): Token

    suspend fun findAccessToken(): String

    suspend fun checkAccessTokenAvailable(): Boolean

    suspend fun findAccessTokenExpiredAt(): String

    suspend fun findRefreshToken(): String

    suspend fun checkRefreshTokenAvailable(): Boolean

    suspend fun findRefreshTokenExpiredAt(): String

    suspend fun saveToken(
        token: Token,
    )

    suspend fun clearToken()
}
