package team.aliens.dms.android.data.auth.repository

import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.network.util.statusMapping
import team.aliens.dms.android.core.school.SchoolProvider
import team.aliens.dms.android.data.auth.exception.BadRequestException
import team.aliens.dms.android.data.auth.exception.PasswordMismatchException
import team.aliens.dms.android.data.auth.exception.UserNotFoundException
import team.aliens.dms.android.data.auth.mapper.extractFeatures
import team.aliens.dms.android.data.auth.mapper.extractTokens
import team.aliens.dms.android.data.auth.model.EmailVerificationType
import team.aliens.dms.android.data.auth.model.HashedEmail
import team.aliens.dms.android.network.auth.datasource.NetworkAuthDataSource
import team.aliens.dms.android.network.auth.model.SendEmailVerificationCodeRequest
import team.aliens.dms.android.network.auth.model.SignInRequest
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val networkAuthDataSource: NetworkAuthDataSource,
    private val jwtProvider: JwtProvider,
    private val schoolProvider: SchoolProvider,
) : AuthRepository() {

    override suspend fun signIn(
        accountId: String,
        password: String,
        deviceToken: String,
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
                    deviceToken = deviceToken,
                ),
            )
        }

        // FIXME: 만약 자동 로그인을 하지 않은 상태에서 토큰 재발급은 어떻게 처리하는가?
        if (autoSignIn) {
            val tokens = signInResponse.extractTokens()
            val features = signInResponse.extractFeatures()
            jwtProvider.updateTokens(tokens)
            schoolProvider.updateFeatures(features)
        }
    }

    override suspend fun sendEmailVerificationCode(
        email: String,
        type: EmailVerificationType,
    ) {
        networkAuthDataSource.sendEmailVerificationCode(
            request = SendEmailVerificationCodeRequest(
                email = email,
                type = type.name,
            ),
        )
    }

    override suspend fun checkEmailVerificationCode(
        email: String,
        code: String,
        type: EmailVerificationType,
    ) {
        networkAuthDataSource.checkEmailVerificationCode(
            email = email,
            code = code,
            type = type.name,
        )
    }

    override suspend fun checkIdExists(accountId: String): HashedEmail =
        networkAuthDataSource.checkIdExists(
            accountId = accountId,
        ).email

    override suspend fun signOut() {
        jwtProvider.clearCaches()
        schoolProvider.clearCaches()
    }
}