package team.aliens.data.remote.datasource.implementation

import team.aliens.data.remote.api.AuthApi
import team.aliens.data.remote.datasource.declaration.RemoteUserDataSource
import team.aliens.data.remote.request.user.GetEmailCodeRequest
import team.aliens.data.remote.request.user.SignInRequest
import team.aliens.data.remote.response.user.SignInResponse
import team.aliens.data.util.HttpHandler
import team.aliens.data.util.sendHttpRequest
import team.aliens.domain.enums.EmailType
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val authApi: AuthApi,
) : RemoteUserDataSource {

    override suspend fun postUserSignIn(signInRequest: SignInRequest): SignInResponse =
        sendHttpRequest(httpRequest = { authApi.postLogin(signInRequest) })

    override suspend fun requestEmailCode(
        requestEmailCodeRequest: GetEmailCodeRequest,
    ) = HttpHandler<Unit>().httpRequest {
        authApi.requestEmailCode(
            requestEmailCodeRequest,
        )
    }.sendRequest()

    override suspend fun checkEmailCode(
        email: String,
        authCode: String,
        type: EmailType,
    ) = HttpHandler<Unit>().httpRequest {
        authApi.checkEmailCode(
            email,
            authCode,
            type,
        )
    }.sendRequest()

    override suspend fun reissueToken(
        refreshToken: String,
    ): SignInResponse {
        return HttpHandler<SignInResponse>().httpRequest {
            authApi.reissueToken(
                refreshToken,
            )
        }.sendRequest()
    }

    override suspend fun compareEmail(
        accountId: String,
        email: String,
    ) = HttpHandler<Unit>().httpRequest {
        authApi.compareEmail(
            accountId,
            email,
        )
    }.sendRequest()

    override suspend fun checkId(
        accountId: String,
    ) = HttpHandler<Unit>().httpRequest {
        authApi.checkId(
            accountId,
        )
    }.sendRequest()
}
