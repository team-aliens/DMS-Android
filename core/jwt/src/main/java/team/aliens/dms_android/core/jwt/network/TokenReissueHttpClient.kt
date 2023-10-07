package team.aliens.dms_android.core.jwt.network

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import team.aliens.dms_android.core.jwt.di.TokenReissueUrl
import team.aliens.dms_android.core.jwt.exception.CannotReissueTokenException
import team.aliens.dms_android.core.network.di.DefaultHttpClient

internal class TokenReissueHttpClient(
    @TokenReissueUrl private val reissueUrl: String,
    @DefaultHttpClient defaultHttpClient: OkHttpClient,
) : OkHttpClient() {

    private val client: OkHttpClient by lazy {
        defaultHttpClient.newBuilder().apply {
            /* config http client */
        }.build()
    }

    operator fun invoke(refreshToken: String): AuthenticationResponse {
        val tokenReissueRequest = buildTokenReissueRequest(refreshToken)
        val response = client.newCall(tokenReissueRequest).execute()

        return if (response.isSuccessful) {
            response.body.toAuthenticationResponse()
        } else {
            throw CannotReissueTokenException()
        }
    }

    private fun ResponseBody?.toAuthenticationResponse(): AuthenticationResponse {
        requireNotNull(this)
        return Gson().fromJson(
            this.string(),
            AuthenticationResponse::class.java,
        )
    }

    private fun buildTokenReissueRequest(refreshToken: String): Request =
        Request.Builder().url(reissueUrl).put(
            body = String().toRequestBody("application/json".toMediaType()),
        ).addHeader(
            name = "refreshToken",
            value = refreshToken,
        ).build()
}