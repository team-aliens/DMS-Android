package team.aliens.data.repository

import team.aliens.data.remote.datasource.declaration.RemoteUserDataSource
import team.aliens.data.remote.request.user.GetEmailCodeRequest
import team.aliens.data.remote.request.user.SignInRequest
import team.aliens.data.remote.response.user.SignInResponse
import team.aliens.domain.entity.user.AuthInfoEntity
import team.aliens.domain.exception.NoInternetException
import team.aliens.domain.param.CheckEmailCodeParam
import team.aliens.domain.param.CompareEmailParam
import team.aliens.domain.param.LoginParam
import team.aliens.domain.param.RequestEmailCodeParam
import team.aliens.domain.repository.UserRepository
import team.aliens.local_database.datasource.declaration.LocalUserDataSource
import team.aliens.local_database.localutil.toLocalDateTime
import team.aliens.local_database.param.FeaturesParam
import team.aliens.local_database.param.UserPersonalKeyParam
import team.aliens.local_database.param.user.UserInfoParam
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
    private val localUserDataSource: LocalUserDataSource,
) : UserRepository {

    override suspend fun userSignIn(loginParam: LoginParam) {
        val response = remoteUserDataSource.postUserSignIn(loginParam.toRequest())
        if (loginParam.autoLogin) {
            localUserDataSource.setUserInfo(UserInfoParam(loginParam.id, loginParam.password))
        }
        localUserDataSource.setUserVisibleInform(response.features.toDbEntity())
        localUserDataSource.setPersonalKey(response.toDbEntity())
    }

    override suspend fun autoSignIn() {
        val info = localUserDataSource.fetchUserInfo()
        try {
            val response =
                remoteUserDataSource.postUserSignIn(signInRequest = SignInRequest(info.id,
                    info.password))
            localUserDataSource.setUserInfo(UserInfoParam(info.id, info.password))
            localUserDataSource.setUserVisibleInform(response.features.toDbEntity())
            localUserDataSource.setPersonalKey(response.toDbEntity())
        } catch (e: NoInternetException) {
            if (info.id.isEmpty() && info.password.isEmpty()) throw e
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

    override suspend fun refreshToken(
        refreshToken: String,
    ) = remoteUserDataSource.refreshToken(refreshToken)

    override suspend fun compareEmail(
        compareEmailParam: CompareEmailParam,
    ) = remoteUserDataSource.compareEmail(
        compareEmailParam.accountId,
        compareEmailParam.email,
    )

    override suspend fun checkId(
        accountId: String,
    ) = remoteUserDataSource.checkId(accountId)

    private fun SignInResponse.toDbEntity() = UserPersonalKeyParam(
        accessToken = accessToken,
        accessTokenExpiredAt = accessTokenExpiredAt.toLocalDateTime(),
        refreshToken = refreshToken,
        refreshTokenExpiredAt = refreshTokenExpiredAt.toLocalDateTime(),
    )

    private fun SignInResponse.Features.toDbEntity() = FeaturesParam(
        mealService = mealService,
        noticeService = noticeService,
        pointService = pointService,
    )

    private fun SignInResponse.toEntity() = AuthInfoEntity(accessToken = accessToken,
        accessTokenExpiredAt = accessTokenExpiredAt.toLocalDateTime(),
        refreshToken = refreshToken,
        refreshTokenExpiredAt = refreshTokenExpiredAt.toLocalDateTime(),
        features = features.toEntity())

    private fun SignInResponse.Features.toEntity() = AuthInfoEntity.Features(
        mealService = mealService,
        noticeService = noticeService,
        pointService = pointService,
    )

    private fun AuthInfoEntity.toDbEntity() = UserPersonalKeyParam(
        accessToken = accessToken,
        accessTokenExpiredAt = accessTokenExpiredAt,
        refreshToken = refreshToken,
        refreshTokenExpiredAt = refreshTokenExpiredAt,
    )

    private fun AuthInfoEntity.Features.toDbEntity() = FeaturesParam(
        mealService = mealService,
        noticeService = noticeService,
        pointService = pointService,
    )

    private fun LoginParam.toRequest() = SignInRequest(
        id = id,
        password = password,
    )

    private fun RequestEmailCodeParam.toRequest() = GetEmailCodeRequest(
        email = email,
        type = type,
    )
}
