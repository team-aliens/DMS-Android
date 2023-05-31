package team.aliens.remote.http

import okhttp3.Interceptor
import okhttp3.Response
import team.aliens.data.facade.AuthorizationFacade
import team.aliens.remote.common.HttpProperty
import team.aliens.remote.common.toHttpMethod
import javax.inject.Inject

// fixme 토큰을 캐싱하여 참조하는 로직이 필요
class AuthorizationInterceptor @Inject constructor(
    private val authorizationFacade: AuthorizationFacade,
    private val ignoreRequestWrapper: IgnoreRequestWrapper,
) : Interceptor {
    override fun intercept(
        chain: Interceptor.Chain,
    ): Response {
        val interceptedRequest: okhttp3.Request = chain.request()

        val request = Request(
            method = interceptedRequest.method.toHttpMethod(),
            path = interceptedRequest.url.encodedPath,
        )

        if (request.shouldBeIgnored()) return chain.proceed(interceptedRequest)
        if (authorizationFacade.accessTokenAvailable.not()) authorizationFacade.reissueAndSaveToken() // check token available()

        val accessToken = authorizationFacade.accessToken()

        return chain.proceed(
            interceptedRequest.newBuilder().addHeader(
                HttpProperty.Header.Authorization,
                HttpProperty.Header.Prefix.Bearer + accessToken,
            ).build(),
        )
    }

    private fun Request.shouldBeIgnored() =
        ignoreRequestWrapper.ignoreRequests.any { it == this }
}
