package team.aliens.data.intercepter

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import team.aliens.data.remote.response.user.SignInResponse
import team.aliens.data.remote.url.DmsUrlProperties
import team.aliens.domain.exception.NeedLoginException
import javax.inject.Inject

class TokenReissueClient @Inject constructor(
    private val reissueUrl: String,
) : OkHttpClient() {

    internal operator fun invoke(
        refreshToken: String?,
    ): SignInResponse {
        requireNotNull(refreshToken)

        val tokenReissueRequest = buildTokenReissueRequest(refreshToken)

        val response = newCall(tokenReissueRequest).execute()

        return if (response.isSuccessful) {
            Gson().fromJson(
                response.body!!.string(),
                SignInResponse::class.java,
            )
        } else {
            throw NeedLoginException()
        }
    }

    private fun buildTokenReissueRequest(
        refreshToken: String,
    ): Request {
        return Request.Builder().url(
            reissueUrl,
        ).put(
            "".toRequestBody(
                DmsUrlProperties.ContentType.APPLICATION_JSON.toMediaTypeOrNull(),
            ),
        ).addHeader(
            DmsUrlProperties.Header.REFRESH_TOKEN,
            refreshToken,
        ).build()
    }
}