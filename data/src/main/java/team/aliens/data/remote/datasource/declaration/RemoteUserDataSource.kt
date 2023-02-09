package team.aliens.data.remote.datasource.declaration

import team.aliens.data.remote.request.user.GetEmailCodeRequest
import team.aliens.data.remote.request.user.SignInRequest
import team.aliens.data.remote.response.user.SignInResponse
import team.aliens.domain.enums.EmailType

interface RemoteUserDataSource {

    suspend fun postUserSignIn(
        signInRequest: SignInRequest,
    ): SignInResponse

    suspend fun requestEmailCode(
        requestEmailCodeRequest: GetEmailCodeRequest,
    )

    suspend fun checkEmailCode(
        email: String,
        authCode: String,
        type: EmailType,
    )

    suspend fun refreshToken(
        refreshToken: String,
    )

    suspend fun compareEmail(
        accountId: String,
        email: String,
    )

    suspend fun checkId(
        accountId: String,
    )
}
