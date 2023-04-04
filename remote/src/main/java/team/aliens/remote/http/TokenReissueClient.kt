package team.aliens.remote.http

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import team.aliens.domain._exception.CommonException
import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model.auth.Token
import team.aliens.remote.common.HttpProperty

class TokenReissueClient(
    private val reissueUrl: String,
) : OkHttpClient() {

    internal operator fun invoke(
        refreshToken: String,
    ): Token {

        val tokenReissueRequest = buildTokenReissueRequest(refreshToken)

        val response = newCall(tokenReissueRequest).execute()

        if (response.isSuccessful) {
            checkNotNull(response.body)

            val token = Gson().fromJson(
                response.body!!.string(),
                AuthenticationOutput::class.java,
            )

            return Token(
                accessToken = token.accessToken,
                accessTokenExpiredAt = token.accessTokenExpiredAt,
                refreshToken = token.refreshToken,
                refreshTokenExpiredAt = token.refreshTokenExpiredAt,
            )
        } else {
            throw CommonException.SignInRequired
        }
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
