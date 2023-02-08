package team.aliens.data.remote.api

import com.example.data.remote.request.user.GetEmailCodeRequest
import com.example.data.remote.request.user.SignInRequest
import com.example.data.remote.response.user.SignInResponse
import com.example.data.remote.url.DmsUrl
import com.example.domain.enums.EmailType
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

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

    @PUT(DmsUrl.User.refreshToken)
    suspend fun refreshToken(
        @Header("refresh-token") refreshToken: String,
    )

    @GET(DmsUrl.User.compareEmail)
    suspend fun compareEmail(
        @Query("account_id") accountId: String,
        @Query("email") email: String,
    )

    @GET(DmsUrl.User.checkId)
    suspend fun checkId(
        @Query("account_id") accountId: String,
    )
}