package team.aliens.remote.http

import java.util.Date
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import team.aliens.data.facade.AuthorizationFacade
import team.aliens.remote.common.HttpProperty
import team.aliens.remote.common.toHttpMethod
import team.aliens.remote.util.toDate
import team.aliens.remote.util.tokenDateFormat

class AuthorizationInterceptor(
    private val authorizationFacade: AuthorizationFacade,
    private val tokenReissueClient: TokenReissueClient,
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

        val requestShouldBeIgnored: Boolean = checkRequestShouldBeIgnored(
            request = request,
        )

        if (requestShouldBeIgnored) return chain.proceed(interceptedRequest)

        val accessTokenAvailable: Boolean = runBlocking { // thread safe한 방식으로 구현 필요
            checkAccessTokenAvailable()
        }

        if (accessTokenAvailable.not()) {
            runBlocking {
                reissueAndSaveToken()
            }
        }

        // todo accesstoken 캐싱 필요
        val accessToken = runBlocking {
            authorizationFacade.accessToken()
        }

        return chain.proceed(
            interceptedRequest.newBuilder().addHeader(
                HttpProperty.Header.Authorization,
                HttpProperty.Header.Prefix.Bearer + accessToken,
            ).build(),
        )
    }

    private fun checkRequestShouldBeIgnored(
        request: Request,
    ): Boolean {
        return ignoreRequestWrapper.ignoreRequests.any { it == request }
    }

    private suspend fun checkAccessTokenAvailable(): Boolean {
        val accessTokenExpiredAt = authorizationFacade.accessTokenExpiredAt().toDate(
            tokenDateFormat,
        )

        val currentTime = Date()

        return currentTime.before(accessTokenExpiredAt)
    }

    private suspend fun reissueAndSaveToken() {

        val refreshToken = authorizationFacade.refreshToken()

        val reissuedToken = tokenReissueClient(
            refreshToken = refreshToken,
        )

        authorizationFacade.saveToken(
            token = reissuedToken,
        )
    }
}
