package team.aliens.dms.android.data.auth.repository

import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.school.SchoolProvider
import team.aliens.dms.android.data.auth.mapper.extractFeatures
import team.aliens.dms.android.data.auth.mapper.extractTokens
import team.aliens.dms.android.data.auth.model.EmailVerificationType
import team.aliens.dms.android.network.auth.datasource.NetworkAuthDataSource
import team.aliens.dms.android.network.auth.model.CheckIdExistsResponse
import team.aliens.dms.android.network.auth.model.SendEmailVerificationCodeRequest
import team.aliens.dms.android.network.auth.model.SignInRequest
import team.aliens.dms.android.network.auth.model.SignInResponse
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
    ): Result<SignInResponse> =
        networkAuthDataSource.signIn(
            request = SignInRequest(
                accountId = accountId,
                password = password,
                deviceToken = deviceToken,
            ),
        ).onSuccess {
            // FIXME: 만약 자동 로그인을 하지 않은 상태에서 토큰 재발급은 어떻게 처리하는가?
            val tokens = it.extractTokens()
            val features = it.extractFeatures()
            jwtProvider.updateTokens(tokens)
            schoolProvider.updateFeatures(features)
        }

    override suspend fun sendEmailVerificationCode(
        email: String,
        type: EmailVerificationType,
    ): Result<Unit> =
        networkAuthDataSource.sendEmailVerificationCode(
            request = SendEmailVerificationCodeRequest(
                email = email,
                type = type.name,
            ),
        )

    override suspend fun checkEmailVerificationCode(
        email: String,
        code: String,
        type: EmailVerificationType,
    ): Result<Unit> =
        networkAuthDataSource.checkEmailVerificationCode(
            email = email,
            code = code,
            type = type.name,
        )

    override suspend fun checkIdExists(accountId: String): Result<CheckIdExistsResponse> =
        networkAuthDataSource.checkIdExists(accountId = accountId)
// TODO :: usecase 활용
//        networkAuthDataSource.checkIdExists(
//            accountId = accountId,
//        ).email



    override suspend fun signOut() = runCatching {
        jwtProvider.clearCaches()
        schoolProvider.clearCaches()
    }
}
