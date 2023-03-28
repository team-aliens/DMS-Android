package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteAuthDataSource
import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model._common.EmailVerificationType
import team.aliens.domain._model.auth.CheckIdExistsOutput
import team.aliens.domain._model.auth.SendEmailVerificationCodeInput
import team.aliens.domain._model.auth.SignInInput

class RemoteAuthDataSourceImpl : RemoteAuthDataSource {

    override suspend fun signIn(
        input: SignInInput,
    ): AuthenticationOutput {
        TODO("Not yet implemented")
    }

    override suspend fun sendEmailVerificationCode(
        input: SendEmailVerificationCodeInput,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun checkEmailVerificationCode(
        email: String,
        authCode: String,
        type: EmailVerificationType,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun reissueToken(): AuthenticationOutput {
        TODO("Not yet implemented")
    }

    override suspend fun verifyEmail(
        accountId: String,
        email: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun checkIdExists(
        accountId: String,
    ): CheckIdExistsOutput {
        TODO("Not yet implemented")
    }

}
