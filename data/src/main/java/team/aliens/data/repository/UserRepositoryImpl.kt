package team.aliens.data.repository

import team.aliens.data.remote.datasource.declaration.RemoteUserDataSource
import team.aliens.data.remote.request.user.GetEmailCodeRequest
import team.aliens.data.remote.request.user.SignInRequest
import team.aliens.data.remote.response.user.SignInResponse
import team.aliens.domain.exception.NeedLoginException
import team.aliens.domain.param.CheckEmailCodeParam
import team.aliens.domain.param.CompareEmailParam
import team.aliens.domain.param.LoginParam
import team.aliens.domain.param.RequestEmailCodeParam
import team.aliens.domain.repository.UserRepository
import team.aliens.local_database.datasource.declaration.LocalUserDataSource
import team.aliens.local_database.localutil.toLocalDateTime
import team.aliens.local_database.param.FeaturesParam
import team.aliens.local_database.param.UserPersonalKeyParam
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localUserDataSource: LocalUserDataSource,
) : UserRepository {

    override suspend fun userSignIn(loginParam: LoginParam) {

        val response = remoteUserDataSource.postUserSignIn(loginParam.toRequest())

        // 혹시 만약 그럴 일은 없겠지만 서비스 이용 중간에 다시 로그인 화면으로 오는 일이
        // 발생할 경우를 대비해서 방어적으로 코드를 작성하였습니다
        localUserDataSource.setAutoSignInOption(loginParam.autoLogin)

        localUserDataSource.setUserVisibleInform(response.features.toDbEntity())

        localUserDataSource.setPersonalKey(response.toDbEntity())
    }

    override suspend fun autoSignIn() {

        val autoSignInEnabled = localUserDataSource.fetchAutoSignInOption()

        if (autoSignInEnabled) {
            val refreshToken = localUserDataSource.fetchPersonalKey().refreshToken
            check(refreshToken.isNotBlank())

            val response = remoteUserDataSource.reissueToken(
                refreshToken = refreshToken,
            )

            val enabledFeatures = response.features.toDbEntity()

            localUserDataSource.setUserVisibleInform(enabledFeatures)
            localUserDataSource.setPersonalKey(response.toDbEntity())
        } else {
            throw NeedLoginException()
        }
    }

    override suspend fun requestEmailCode(
        requestEmailCodeParam: RequestEmailCodeParam,
    ) = remoteUserDataSource.requestEmailCode(requestEmailCodeParam.toRequest())

    override suspend fun checkEmailCode(
        checkEmailCodeParam: CheckEmailCodeParam,
    ) = remoteUserDataSource.checkEmailCode(
        checkEmailCodeParam.email,
        checkEmailCodeParam.authCode,
        checkEmailCodeParam.type,
    )

    override suspend fun reissueToken(
        refreshToken: String,
    ) {

        val newTokens = remoteUserDataSource.reissueToken(refreshToken).toDbEntity()

        localUserDataSource.setPersonalKey(newTokens)
    }

    override suspend fun compareEmail(
        compareEmailParam: CompareEmailParam,
    ) = remoteUserDataSource.compareEmail(
        compareEmailParam.accountId,
        compareEmailParam.email,
    )

    override suspend fun checkId(
        accountId: String,
    ) = remoteUserDataSource.checkId(accountId)

    override suspend fun signOut() {
        localUserDataSource.signOut()
    }
}

private fun SignInResponse.toDbEntity(): UserPersonalKeyParam {
    return UserPersonalKeyParam(
        accessToken = accessToken,
        accessTokenExpiredAt = accessTokenExpiredAt.toLocalDateTime(),
        refreshToken = refreshToken,
        refreshTokenExpiredAt = refreshTokenExpiredAt.toLocalDateTime(),
    )
}

private fun SignInResponse.Features.toDbEntity(): FeaturesParam {
    return FeaturesParam(
        mealService = mealService,
        noticeService = noticeService,
        pointService = pointService,
    )
}

private fun LoginParam.toRequest(): SignInRequest {
    return SignInRequest(
        id = id,
        password = password,
    )
}

private fun RequestEmailCodeParam.toRequest(): GetEmailCodeRequest {
    return GetEmailCodeRequest(
        email = email,
        type = type,
    )
}
