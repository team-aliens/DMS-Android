package team.aliens.remote.http

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import team.aliens.domain._exception.CommonException
import team.aliens.remote.common.HttpProperty
import team.aliens.remote.model._common.AuthenticationResponse

class TokenReissueClient(
    private val reissueUrl: String,
) : OkHttpClient() {

    internal operator fun invoke(
        refreshToken: String,
    ): AuthenticationResponse {

        val tokenReissueRequest = buildTokenReissueRequest(refreshToken)

        val response = newCall(tokenReissueRequest).execute()

        return if (response.isSuccessful) {
            checkNotNull(response.body)

            Gson().fromJson(
                response.body!!.string(),
                AuthenticationResponse::class.java,
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
