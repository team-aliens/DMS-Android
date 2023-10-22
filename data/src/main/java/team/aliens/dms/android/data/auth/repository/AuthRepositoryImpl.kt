package team.aliens.dms.android.data.auth.repository

import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.network.util.statusMapping
import team.aliens.dms.android.data.auth.exception.BadRequestException
import team.aliens.dms.android.data.auth.exception.PasswordMismatchException
import team.aliens.dms.android.data.auth.exception.UserNotFoundException
import team.aliens.dms.android.data.auth.mapper.extractTokens
import team.aliens.dms.android.data.auth.model.EmailVerificationType
import team.aliens.dms.android.data.auth.model.HashedEmail
import team.aliens.dms.android.network.auth.datasource.NetworkAuthDataSource
import team.aliens.dms.android.network.auth.model.SignInRequest
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val networkAuthDataSource: NetworkAuthDataSource,
    private val jwtProvider: JwtProvider,
) : AuthRepository() {

    override suspend fun signIn(
        accountId: String,
        password: String,
        autoSignIn: Boolean,
    ) {
        val signInResponse = statusMapping(
            onBadRequest = { throw BadRequestException() },
            onUnauthorized = { throw PasswordMismatchException() },
            onNotFound = { throw UserNotFoundException() },
        ) {
            networkAuthDataSource.signIn(
                request = SignInRequest(
                    accountId = accountId,
                    password = password,
                ),
            )
        }

        if (autoSignIn) {
            val tokens = signInResponse.extractTokens()
            jwtProvider.saveTokens(tokens)
        }
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