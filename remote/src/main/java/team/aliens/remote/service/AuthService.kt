package team.aliens.remote.service

import retrofit2.http.*
import team.aliens.remote.annotation.RequiresRefreshToken
import team.aliens.remote.common.DormHttpPath.Auth.CheckEmailVerificationCode
import team.aliens.remote.common.DormHttpPath.Auth.CheckIdExists
import team.aliens.remote.common.DormHttpPath.Auth.ReissueToken
import team.aliens.remote.common.DormHttpPath.Auth.SendEmailVerificationCode
import team.aliens.remote.common.DormHttpPath.Auth.SignIn
import team.aliens.remote.common.DormHttpPath.Auth.VerifyEmail
import team.aliens.remote.common.HttpProperty.Header.RefreshToken
import team.aliens.remote.common.HttpProperty.QueryString.AccountId
import team.aliens.remote.common.HttpProperty.QueryString.AuthCode
import team.aliens.remote.common.HttpProperty.QueryString.Email
import team.aliens.remote.common.HttpProperty.QueryString.Type
import team.aliens.remote.model._common.AuthenticationResponse
import team.aliens.remote.model.auth.CheckIdExistsResponse
import team.aliens.remote.model.auth.SendEmailVerificationCodeRequest
import team.aliens.remote.model.auth.SignInRequest
import team.aliens.remote.model.auth.VerifyEmailResponse

interface AuthService {

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
    @RequiresRefreshToken
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
