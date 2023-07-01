package team.aliens.remote.http

import java.util.regex.Pattern
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response
import team.aliens.data.facade.AuthorizationFacade
import team.aliens.remote.common.HttpProperty
import team.aliens.remote.common.toHttpMethod

// todo 토큰을 캐싱하여 참조하는 로직이 필요
class AuthorizationInterceptor @Inject constructor(
    private val authorizationFacade: AuthorizationFacade,
    private val ignoreRequestWrapper: IgnoreRequestWrapper,
) : Interceptor {
    override fun intercept(
        chain: Interceptor.Chain,
    ): Response {
        val interceptedRequest: okhttp3.Request = chain.request()

        return chain.proceed(
            if (interceptedRequest.shouldBeIgnored()) {
                interceptedRequest
            } else {
                interceptedRequest.newBuilder().addAccessToken().build()
            }
        )
    }

    private fun okhttp3.Request.Builder.addAccessToken(): okhttp3.Request.Builder {
        val accessToken = authorizationFacade.accessTokenOrReissue()

        return this.addHeader(
            HttpProperty.Header.Authorization,
            HttpProperty.Header.Prefix.Bearer + accessToken,
        )
    }

    private fun okhttp3.Request.shouldBeIgnored(): Boolean {
        val request = Request(
            method = this.method.toHttpMethod(),
            path = this.url.encodedPath,
        )

        return ignoreRequestWrapper.ignoreRequests.any {
            Pattern.compile(it.path).matcher(request.path).matches() && it.method == request.method
        }
    }
}
