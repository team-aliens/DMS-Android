package team.aliens.data.datasource.remote

import team.aliens.dms_android.domain.model._common.AuthenticationOutput
import team.aliens.domain.model._common.EmailVerificationType
import team.aliens.domain.model.auth.CheckIdExistsInput
import team.aliens.domain.model.auth.CheckIdExistsOutput
import team.aliens.domain.model.auth.SendEmailVerificationCodeInput
import team.aliens.domain.model.auth.SignInInput

interface RemoteAuthDataSource {

    suspend fun signIn(
        input: SignInInput,
    ): team.aliens.dms_android.domain.model._common.AuthenticationOutput

    suspend fun sendEmailVerificationCode(
        input: SendEmailVerificationCodeInput,
    )

    suspend fun checkEmailVerificationCode(
        email: String,
        authCode: String,
        type: EmailVerificationType,
    )

    suspend fun reissueToken(
        refreshToken: String,
    ): team.aliens.dms_android.domain.model._common.AuthenticationOutput

    suspend fun verifyEmail(
        accountId: String,
        email: String,
    )

    suspend fun checkIdExists(
        input: CheckIdExistsInput,
    ): CheckIdExistsOutput
}
