package team.aliens.dms_android.network.http

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import team.aliens.data.manager.TokenReissueManager
import team.aliens.dms_android.network.annotation.TokenReissueUrl
import team.aliens.dms_android.network.common.HttpProperty
import team.aliens.dms_android.network.model._common.AuthenticationResponse
import team.aliens.dms_android.network.model._common.toDomain
import team.aliens.dms_android.domain.exception.CommonException
import team.aliens.dms_android.domain.model._common.AuthenticationOutput
import team.aliens.domain.model.auth.Token
import javax.inject.Inject

class TokenReissueManagerImpl @Inject constructor(
    @TokenReissueUrl private val reissueUrl: String,
) : TokenReissueManager {
    private val client: OkHttpClient by lazy {
        OkHttpClient()
    }

    override fun reissueToken(refreshToken: String): _root_ide_package_.team.aliens.dms_android.domain.model._common.AuthenticationOutput {
        val tokenReissueRequest = buildTokenReissueRequest(refreshToken)
        val response = client.newCall(tokenReissueRequest).execute()

        return if (response.isSuccessful) {
            response.body.toAuthenticationOutput()
        } else {
            throw team.aliens.dms_android.domain.exception.CommonException.SignInRequired
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

    private fun ResponseBody?.toAuthenticationOutput(): _root_ide_package_.team.aliens.dms_android.domain.model._common.AuthenticationOutput {
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
