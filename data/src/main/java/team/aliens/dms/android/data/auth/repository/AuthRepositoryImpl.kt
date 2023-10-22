package team.aliens.dms.android.data.auth.repository

import team.aliens.dms.android.data.auth.model.EmailVerificationType
import team.aliens.dms.android.data.auth.model.HashedEmail
import team.aliens.dms.android.network.auth.datasource.NetworkAuthDataSource
import team.aliens.dms.android.network.auth.model.SignInRequest
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val networkAuthDataSource: NetworkAuthDataSource,
) : AuthRepository() {

    override suspend fun signIn(
        accountId: String,
        password: String,
        autoSignIn: Boolean,
    ) {
        networkAuthDataSource.signIn(
            request = SignInRequest(
                accountId = accountId,
                password = password,
            ),
        )
    }

    override suspend fun sendEmailVerificationCode(
        email: String,
        type: EmailVerificationType,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun examineEmailVerificationCode(
        email: String,
        code: String,
        type: EmailVerificationType,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun checkIdExists(accountId: String): HashedEmail {
        TODO("Not yet implemented")
    }
}