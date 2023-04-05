package team.aliens.data.repository

import team.aliens.data.remote.datasource.declaration.RemoteUserDataSource
import team.aliens.data.remote.request.user.EditPasswordRequest
import team.aliens.data.remote.request.user.GetEmailCodeRequest
import team.aliens.data.remote.request.user.SignInRequest
import team.aliens.data.remote.response.user.CheckIdResponse
import team.aliens.data.remote.response.user.SignInResponse
import team.aliens.domain.entity.user.CheckIdEntity
import team.aliens.domain.exception.NeedLoginException
import team.aliens.domain.param.*
import team.aliens.domain.repository.UserRepository
import team.aliens.local_database.datasource.declaration.LocalUserDataSource
import team.aliens.local_database.localutil.toLocalDateTime
import team.aliens.local_database.param.UserVisibleParam
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

        localUserDataSource.setUserVisibleInform(response.features.toParam())

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

            val enabledFeatures = response.features.toParam()

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
    ): CheckIdEntity = remoteUserDataSource.checkId(accountId).toEntity()

    override suspend fun editPassword(
        editPasswordParam: EditPasswordParam,
    ) = remoteUserDataSource.editPassword(
        editPasswordRequest = editPasswordParam.toRequest(),
    )

    override suspend fun comparePassword(
        password: String,
    ) = remoteUserDataSource.comparePassword(
        password = password,
    )

    override suspend fun signOut() {
        localUserDataSource.signOut()
    }

    override suspend fun fetchAutoSignInOption(): Boolean {
        return localUserDataSource.fetchAutoSignInOption()
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

private fun CheckIdResponse.toEntity(): CheckIdEntity {
    return CheckIdEntity(
        email = this.email,
    )
}

private fun SignInResponse.Features.toParam(): UserVisibleParam {
    return UserVisibleParam(
        mealService = mealService,
        noticeService = noticeService,
        pointService = pointService,
        studyRoomService = studyRoomService,
        remainService = remainService,
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

private fun EditPasswordParam.toRequest(): EditPasswordRequest{
    return EditPasswordRequest(
        currentPassword = currentPassword,
        newPassword = newPassword,
    )
}
