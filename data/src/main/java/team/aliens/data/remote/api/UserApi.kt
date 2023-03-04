package team.aliens.data.remote.api

import retrofit2.Response
import retrofit2.http.*
import team.aliens.data.remote.request.user.EditPasswordRequest
import team.aliens.data.remote.request.user.GetEmailCodeRequest
import team.aliens.data.remote.request.user.SignInRequest
import team.aliens.data.remote.response.user.SignInResponse
import team.aliens.data.remote.url.DmsUrl
import team.aliens.domain.enums.EmailType

interface UserApi {

    @POST(DmsUrl.User.login)
    suspend fun postLogin(
        @Body signInRequest: SignInRequest,
    ): SignInResponse

    @POST(DmsUrl.User.emailCode)
    suspend fun requestEmailCode(
        @Body requestEmailCodeRequest: GetEmailCodeRequest,
    )

    @GET(DmsUrl.User.emailCode)
    suspend fun checkEmailCode(
        @Query("email") email: String,
        @Query("auth_code") authCode: String,
        @Query("type") type: EmailType,
    )

    @PUT(DmsUrl.User.reissueToken)
    suspend fun reissueToken(
        @Header("refresh-token") refreshToken: String,
    ): SignInResponse

    @GET(DmsUrl.User.compareEmail)
    suspend fun compareEmail(
        @Query("account_id") accountId: String,
        @Query("email") email: String,
    )

    @GET(DmsUrl.User.checkId)
    suspend fun checkId(
        @Query("account_id") accountId: String,
    )

    @PATCH(DmsUrl.User.editPassword)
    suspend fun editPassword(
        @Body editPasswordRequest: EditPasswordRequest,
    ): Response<Void>

    @GET(DmsUrl.User.comparePassword)
    suspend fun comparePassword(
        @Query("password") password: String,
    )
}