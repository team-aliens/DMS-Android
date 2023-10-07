package team.aliens.remote.http

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import team.aliens.data.manager.TokenReissueManager
import team.aliens.domain.exception.CommonException
import team.aliens.domain.model._common.AuthenticationOutput
import team.aliens.domain.model.auth.Token
import team.aliens.remote.annotation.TokenReissueUrl
import team.aliens.remote.common.HttpProperty
import team.aliens.remote.model._common.AuthenticationResponse
import team.aliens.remote.model._common.toDomain
import javax.inject.Inject

class TokenReissueManagerImpl @Inject constructor(
    @TokenReissueUrl private val reissueUrl: String,
) : TokenReissueManager {
    private val client: OkHttpClient by lazy {
        OkHttpClient()
    }

    override fun reissueToken(refreshToken: String): AuthenticationOutput {
        val tokenReissueRequest = buildTokenReissueRequest(refreshToken)
        val response = client.newCall(tokenReissueRequest).execute()

        return if (response.isSuccessful) {
            response.body.toAuthenticationOutput()
        } else {
            throw CommonException.SignInRequired
        }
    }

    private fun ResponseBody?.toToken(): Token {
        checkNotNull(this)

        val token = Gson().fromJson(
            this.string(),
            AuthenticationResponse::class.java,
        )

        return Token(
            accessToken = token.accessToken,
            accessTokenExpiredAt = token.accessTokenExpiredAt,
            refreshToken = token.refreshToken,
            refreshTokenExpiredAt = token.refreshTokenExpiredAt,
        )
    }

    private fun ResponseBody?.toAuthenticationOutput(): AuthenticationOutput {
        checkNotNull(this)

        val response = Gson().fromJson(
            this.string(),
            AuthenticationResponse::class.java,
        )

        return response.toDomain()
    }

    private fun buildTokenReissueRequest(refreshToken: String): Request {
        return Request.Builder().url(
            reissueUrl,
        ).put(
            String().toRequestBody(
                HttpProperty.Header.ContentType.Application.Json.toMediaType(),
            ),
        ).addHeader(
            HttpProperty.Header.RefreshToken,
            refreshToken,
        ).build()
    }
}