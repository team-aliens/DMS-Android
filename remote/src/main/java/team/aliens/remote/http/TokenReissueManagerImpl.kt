package team.aliens.remote.http

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import team.aliens.data.manager.TokenReissueManager
import team.aliens.domain.exception.CommonException
import team.aliens.domain.model.auth.Token
import team.aliens.remote.annotation.TokenReissueUrl
import team.aliens.remote.common.HttpProperty
import team.aliens.remote.model._common.AuthenticationResponse
import javax.inject.Inject

class TokenReissueManagerImpl @Inject constructor(
    @TokenReissueUrl private val reissueUrl: String,
) : OkHttpClient(), TokenReissueManager {
    override fun reissueToken(refreshToken: String): Token {
        val tokenReissueRequest = buildTokenReissueRequest(refreshToken)
        val response = newCall(tokenReissueRequest).execute()
        return if (response.isSuccessful) response.body.toToken() else throw CommonException.SignInRequired
    }

    private fun ResponseBody?.toToken(): Token {
        requireNotNull(this)

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

    private fun buildTokenReissueRequest(
        refreshToken: String,
    ): Request {
        return Request.Builder().url(
            reissueUrl,
        ).put(
            "".toRequestBody(
                HttpProperty.Header.ContentType.Application.Json.toMediaType(),
            ),
        ).addHeader(
            HttpProperty.Header.RefreshToken,
            refreshToken,
        ).build()
    }
}
