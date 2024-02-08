package team.aliens.dms.android.core.jwt.network

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import team.aliens.dms.android.core.jwt.network.exception.CannotReissueTokenException
import team.aliens.dms.android.core.jwt.network.model.TokensResponse
import javax.inject.Inject

class JwtReissueManager @Inject constructor(
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
        val request = buildTokenReissueRequest(refreshToken)
        val response = client.newCall(request).execute()

        return if (response.isSuccessful) {
            response.body.toTokensResponse()
        } else {
            throw CannotReissueTokenException()
        }
    }

    private fun ResponseBody?.toTokensResponse(): TokensResponse {
        requireNotNull(this)
        return Gson().fromJson(this.string(), TokensResponse::class.java)
    }

    private fun buildTokenReissueRequest(refreshToken: String): Request =
        Request.Builder().url(reissueUrl).put(
            body = String().toRequestBody("application/json".toMediaType()),
        ).addHeader(
            name = "refresh-token",
            value = refreshToken,
        ).build()
}
