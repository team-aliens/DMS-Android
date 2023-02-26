package team.aliens.data.remote.api

import retrofit2.http.*
import team.aliens.data.remote.request.user.GetEmailCodeRequest
import team.aliens.data.remote.request.user.SignInRequest
import team.aliens.data.remote.response.user.SignInResponse
import team.aliens.data.remote.url.DmsUrl
import team.aliens.domain.enums.EmailType

interface AuthApi {

    @POST(DmsUrl.Auth.login)
    suspend fun postLogin(
        @Body signInRequest: SignInRequest,
    ): SignInResponse

    @POST(DmsUrl.Auth.emailCode)
    suspend fun requestEmailCode(
        @Body requestEmailCodeRequest: GetEmailCodeRequest,
    )

    @GET(DmsUrl.Auth.emailCode)
    suspend fun checkEmailCode(
        @Query("email") email: String,
        @Query("auth_code") authCode: String,
        @Query("type") type: EmailType,
    )

    @PUT(DmsUrl.Auth.reissueToken)
    suspend fun reissueToken(
        @Header("refresh-token") refreshToken: String,
    ): SignInResponse

    @GET(DmsUrl.Auth.compareEmail)
    suspend fun compareEmail(
        @Query("account_id") accountId: String,
        @Query("email") email: String,
    )

    @GET(DmsUrl.Auth.checkId)
    suspend fun checkId(
        @Query("account_id") accountId: String,
    )
}