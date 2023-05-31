package team.aliens.remote.http

import java.util.Date
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import team.aliens.data.facade.AuthorizationFacade
import team.aliens.domain.exception.CommonException
import team.aliens.remote.common.HttpProperty
import team.aliens.remote.common.toHttpMethod
import team.aliens.remote.util.toDate
import team.aliens.remote.util.tokenDateFormat
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val authorizationFacade: AuthorizationFacade,
    private val tokenReissueClient: TokenReissueClient,
    private val ignoreRequestWrapper: IgnoreRequestWrapper,
) : Interceptor {
    override fun intercept(
        chain: Interceptor.Chain,
    ): Response {
        val interceptedRequest: okhttp3.Request = chain.request()
        fun proceed() = chain.proceed(interceptedRequest)

        val request = Request(
            method = interceptedRequest.method.toHttpMethod(),
            path = interceptedRequest.url.encodedPath,
        )

        val requestShouldBeIgnored: Boolean = checkRequestShouldBeIgnored(
            request = request,
        )

        if (requestShouldBeIgnored) return proceed()

        val accessTokenAvailable: Boolean = runBlocking { // thread safe한 방식으로 구현 필요
            checkAccessTokenAvailable()
        }

        if (accessTokenAvailable.not()) {
            runBlocking {
                reissueAndSaveToken()
            }
        }

        val accessToken = fetchAccessTokenOrThrow()

        return chain.proceed(
            interceptedRequest.newBuilder().addHeader(
                HttpProperty.Header.Authorization,
                HttpProperty.Header.Prefix.Bearer + accessToken,
            ).build(),
        )
    }

    private fun fetchAccessTokenOrThrow(): String {
        return runBlocking {
            try {
                authorizationFacade.accessToken()
            } catch (e: Exception) {
                throw CommonException.SignInRequired
            }
        }
    }

    private fun checkRequestShouldBeIgnored(
        request: Request,
    ): Boolean {
        return ignoreRequestWrapper.ignoreRequests.any { it == request }
    }

    private suspend fun checkAccessTokenAvailable(): Boolean {
        val currentTime = Date()
        val accessTokenExpiredAt =
            authorizationFacade.accessTokenExpiredAt().toDate(tokenDateFormat)
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
