package team.aliens.dms.android.network.auth.apiservice

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import team.aliens.dms.android.network.auth.model.CheckIdExistsResponse
import team.aliens.dms.android.network.auth.model.SendEmailVerificationCodeRequest
import team.aliens.dms.android.network.auth.model.SignInRequest
import team.aliens.dms.android.network.auth.model.SignInResponse

internal interface AuthApiService {

    @POST("/auth/tokens")
    suspend fun signIn(@Body request: SignInRequest): SignInResponse

    @POST("/auth/code")
    suspend fun sendEmailVerificationCode(@Body request: SendEmailVerificationCodeRequest)

    @GET("/auth/code")
    suspend fun checkEmailVerificationCode(
        @Query("email") email: String,
        @Query("auth_code") authCode: String,
        @Query("type") type: String,
    )

    @GET("/auth/account-id")
    suspend fun checkIdExists(@Query("account_id") accountId: String): CheckIdExistsResponse
}
