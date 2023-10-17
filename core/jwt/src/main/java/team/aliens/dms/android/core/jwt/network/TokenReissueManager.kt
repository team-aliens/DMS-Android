package team.aliens.dms.android.core.jwt.network

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import team.aliens.dms.android.core.jwt.exception.CannotReissueTokenException
import team.aliens.dms.android.core.jwt.network.model.TokensResponse

class TokenReissueManager(
    private val reissueUrl: String,
    private val httpLoggingInterceptor: HttpLoggingInterceptor,
    baseHttpClient: OkHttpClient,
) {
    private val client: OkHttpClient by lazy {
        baseHttpClient.newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
        }.build()
    }

    operator fun invoke(refreshToken: String): TokensResponse {
        val tokenReissueRequest = buildTokenReissueRequest(refreshToken)
        val response = client.newCall(tokenReissueRequest).execute()

        return if (response.isSuccessful) {
            response.body.toAuthenticationResponse()
        } else {
            throw CannotReissueTokenException()
        }
    }

    private fun ResponseBody?.toAuthenticationResponse(): TokensResponse {
        requireNotNull(this)
        return Gson().fromJson(
            this.string(),
            TokensResponse::class.java,
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
