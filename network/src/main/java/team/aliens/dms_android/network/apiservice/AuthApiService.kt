package team.aliens.dms_android.network.apiservice

import retrofit2.http.*
import team.aliens.dms_android.network.annotation.RequiresRefreshToken
import team.aliens.network.common.HttpPath.Auth.CheckEmailVerificationCode
import team.aliens.network.common.HttpPath.Auth.CheckIdExists
import team.aliens.network.common.HttpPath.Auth.ReissueToken
import team.aliens.network.common.HttpPath.Auth.SendEmailVerificationCode
import team.aliens.network.common.HttpPath.Auth.SignIn
import team.aliens.network.common.HttpPath.Auth.VerifyEmail
import team.aliens.network.common.HttpProperty.Header.RefreshToken
import team.aliens.network.common.HttpProperty.QueryString.AccountId
import team.aliens.network.common.HttpProperty.QueryString.AuthCode
import team.aliens.network.common.HttpProperty.QueryString.Email
import team.aliens.network.common.HttpProperty.QueryString.Type
import team.aliens.network.model._common.AuthenticationResponse
import team.aliens.network.model.auth.CheckIdExistsResponse
import team.aliens.network.model.auth.SendEmailVerificationCodeRequest
import team.aliens.network.model.auth.SignInRequest
import team.aliens.network.model.auth.VerifyEmailResponse

interface AuthApiService {

    @POST(SignIn)
    suspend fun signIn(
        @Body request: SignInRequest,
    ): AuthenticationResponse

    @POST(SendEmailVerificationCode)
    suspend fun sendEmailVerificationCode(
        @Body request: SendEmailVerificationCodeRequest,
    )

    @GET(CheckEmailVerificationCode)
    suspend fun checkEmailVerificationCode(
        @Query(Email) email: String,
        @Query(AuthCode) authCode: String,
        @Query(Type) type: String,
    )

    @PUT(ReissueToken)
    @team.aliens.dms_android.network.annotation.RequiresRefreshToken
    suspend fun reissueToken(
        @Header(RefreshToken) refreshToken: String,
    ): AuthenticationResponse

    @GET(VerifyEmail)
    suspend fun verifyEmail(
        @Query(AccountId) accountId: String,
        @Query(Email) email: String,
    ): VerifyEmailResponse

    @GET(CheckIdExists)
    suspend fun checkIdExists(
        @Query(AccountId) accountId: String,
    ): CheckIdExistsResponse
}
