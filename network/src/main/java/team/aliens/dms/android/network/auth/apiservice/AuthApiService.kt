package team.aliens.dms.android.network.auth.apiservice

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import team.aliens.dms.android.network.auth.model.EmailVerificationType

internal abstract class AuthApiService {

    @POST("/auth/tokens")
    abstract suspend fun signIn(@Body request: SignInRequest): AuthenticationResponse

    @POST("/auth/code")
    abstract suspend fun sendEmailVerificationCode(@Body request: SendEmailVerificationCodeRequest)

    @GET("/auth/code")
    abstract suspend fun checkEmailVerificationCode(
        @Query("email") email: String,
        @Query("auth_code") authCode: String,
        @Query("type") type: EmailVerificationType,
    )

    @GET("/auth/account-id")
    abstract suspend fun checkIdExists(@Query("account_id") accountId: String): CheckIdExistsResponse
}
