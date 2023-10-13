package team.aliens.dms.android.network.apiservice

import retrofit2.http.*
import team.aliens.dms.android.network.annotation.RequiresRefreshToken
import team.aliens.dms.android.network.common.HttpPath.Auth.CheckEmailVerificationCode
import team.aliens.dms.android.network.common.HttpPath.Auth.CheckIdExists
import team.aliens.dms.android.network.common.HttpPath.Auth.ReissueToken
import team.aliens.dms.android.network.common.HttpPath.Auth.SendEmailVerificationCode
import team.aliens.dms.android.network.common.HttpPath.Auth.SignIn
import team.aliens.dms.android.network.common.HttpPath.Auth.VerifyEmail
import team.aliens.dms.android.network.common.HttpProperty.Header.RefreshToken
import team.aliens.dms.android.network.common.HttpProperty.QueryString.AccountId
import team.aliens.dms.android.network.common.HttpProperty.QueryString.AuthCode
import team.aliens.dms.android.network.common.HttpProperty.QueryString.Email
import team.aliens.dms.android.network.common.HttpProperty.QueryString.Type
import team.aliens.dms.android.network.model._common.AuthenticationResponse
import team.aliens.dms.android.network.model.auth.CheckIdExistsResponse
import team.aliens.dms.android.network.model.auth.SendEmailVerificationCodeRequest
import team.aliens.dms.android.network.model.auth.SignInRequest
import team.aliens.dms.android.network.model.auth.VerifyEmailResponse

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
