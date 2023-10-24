package team.aliens.dms.android.core.jwt.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Request.Builder
import okhttp3.Response
import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.jwt.network.IgnoreRequests
import team.aliens.dms.android.core.network.toHttpMethod
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
                interceptedRequest.newBuilder().addAccessToken().build()
            }
        )
    }

    private fun Builder.addAccessToken(): Builder = this@addAccessToken.addHeader(
        name = "authorization",
        value = "bearer ${jwtProvider.cachedAccessToken}",
    )

    private fun Request.shouldBeIgnored(): Boolean = ignoreRequests.requests.any { ignoreRequest ->
        val path = this@shouldBeIgnored.url.encodedPath
        val method = this@shouldBeIgnored.method.toHttpMethod()

        path.contains(ignoreRequest.path) && method == ignoreRequest.method
    }
}
