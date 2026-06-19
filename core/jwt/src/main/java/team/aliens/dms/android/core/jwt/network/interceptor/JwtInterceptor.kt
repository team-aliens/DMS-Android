package team.aliens.dms.android.core.jwt.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Request.Builder
import okhttp3.Response
import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.jwt.exception.CannotUseAccessTokenException
import team.aliens.dms.android.core.jwt.network.IgnoreRequests
import team.aliens.dms.android.core.network.toHttpMethod
import team.aliens.dms.android.core.network.util.ResourceKeys
import javax.inject.Inject

class JwtInterceptor @Inject constructor(
    private val jwtProvider: JwtProvider,
    private val ignoreRequests: IgnoreRequests,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val interceptedRequest: Request = chain.request()

        return chain.proceed(
            if (interceptedRequest.shouldBeIgnored()) {
                interceptedRequest
            } else {
                runCatching {
                    interceptedRequest.newBuilder().addAccessToken().build()
                }.getOrElse { exception ->
                    when (exception) {
                        is CannotUseAccessTokenException -> interceptedRequest
                        else -> throw exception
                    }
                }
            },
        )
    }

    private fun Builder.addAccessToken(): Builder = this@addAccessToken.header(
        name = "authorization",
        value = "Bearer ${jwtProvider.cachedAccessToken.value}",
    )

    private fun Request.shouldBeIgnored(): Boolean {
        if (checkS3Request(url.toString())) {
            return true
        }

        return ignoreRequests.requests.any { ignoreRequest ->
            val path = url.encodedPath
            val requestMethod = method.toHttpMethod()

            path.contains(ignoreRequest.path) &&
                    requestMethod == ignoreRequest.method
        }
    }

    private fun checkS3Request(url: String): Boolean {
        return url.contains(ResourceKeys.IMAGE_URL)
    }
}
