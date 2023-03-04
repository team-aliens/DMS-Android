package team.aliens.data.remote.datasource.implementation

import team.aliens.data.remote.api.UserApi
import team.aliens.data.remote.datasource.declaration.RemoteUserDataSource
import team.aliens.data.remote.request.user.EditPasswordRequest
import team.aliens.data.remote.request.user.GetEmailCodeRequest
import team.aliens.data.remote.request.user.SignInRequest
import team.aliens.data.remote.response.user.CheckIdResponse
import team.aliens.data.remote.response.user.SignInResponse
import team.aliens.data.util.HttpHandler
import team.aliens.data.util.sendHttpRequest
import team.aliens.domain.enums.EmailType
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : RemoteUserDataSource {

    override suspend fun postUserSignIn(signInRequest: SignInRequest): SignInResponse =
        sendHttpRequest(httpRequest = { userApi.postLogin(signInRequest) })

    override suspend fun requestEmailCode(
        requestEmailCodeRequest: GetEmailCodeRequest,
    ) = HttpHandler<Unit>().httpRequest {
        userApi.requestEmailCode(
            requestEmailCodeRequest,
        )
    }.sendRequest()

    override suspend fun checkEmailCode(
        email: String,
        authCode: String,
        type: EmailType,
    ) = HttpHandler<Unit>().httpRequest {
        userApi.checkEmailCode(
            email,
            authCode,
            type,
        )
    }.sendRequest()

    override suspend fun reissueToken(
        refreshToken: String,
    ): SignInResponse {
        return HttpHandler<SignInResponse>().httpRequest {
            userApi.reissueToken(
                refreshToken,
            )
        }.sendRequest()
    }

    override suspend fun compareEmail(
        accountId: String,
        email: String,
    ) = HttpHandler<Unit>().httpRequest {
        userApi.compareEmail(
            accountId,
            email,
        )
    }.sendRequest()

    override suspend fun checkId(
        accountId: String,
    ): CheckIdResponse = HttpHandler<CheckIdResponse>()
            .httpRequest {
                userApi.checkId(
                    accountId,
                )
            }.sendRequest()

    override suspend fun editPassword(
        editPasswordRequest: EditPasswordRequest,
    ) = HttpHandler<Unit>()
        .httpRequest {
            userApi.editPassword(
                editPasswordRequest = editPasswordRequest,
            )
        }.sendRequest()


    override suspend fun comparePassword(
        password: String,
    ) = HttpHandler<Unit>().httpRequest {
        userApi.comparePassword(
            password = password
        )
    }.sendRequest()
}

