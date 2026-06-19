package team.aliens.dms.android.core.jwt.network.authenticator

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.jwt.network.IgnoreRequests
import team.aliens.dms.android.core.network.toHttpMethod
import team.aliens.dms.android.core.network.util.ResourceKeys
import javax.inject.Inject

class JwtAuthenticator @Inject constructor(
    private val jwtProvider: JwtProvider,
    private val ignoreRequests: IgnoreRequests,
) : Authenticator {
    override fun authenticate(
        route: Route?,
        response: Response,
    ): Request? {
        val request = response.request

        if (
            request.shouldBeIgnored() ||
            response.retryCount >= MAX_AUTH_RETRY_COUNT
        ) {
            return null
        }

        val failedAuthorization = request.header(AUTHORIZATION_HEADER)
        val newAuthorization = refreshAuthorization(
            failedAuthorization = failedAuthorization,
        )

        return newAuthorization
            ?.takeUnless { authorization ->
                authorization == failedAuthorization
            }
            ?.let { authorization ->
                request.newBuilder()
                    .header(AUTHORIZATION_HEADER, authorization)
                    .build()
            }
    }

    @Synchronized
    private fun refreshAuthorization(
        failedAuthorization: String?,
    ): String? {
        val currentAuthorization = runCatching {
            "Bearer ${jwtProvider.cachedAccessToken.value}"
        }.getOrNull()

        if (
            currentAuthorization != null &&
            currentAuthorization != failedAuthorization
        ) {
            return currentAuthorization
        }

        val refreshed = runCatching {
            runBlocking {
                jwtProvider.refreshSession()
            }
        }.getOrDefault(false)

        if (!refreshed) {
            return null
        }

        return runCatching {
            "Bearer ${jwtProvider.cachedAccessToken.value}"
        }.getOrNull()
    }

    private fun Request.shouldBeIgnored(): Boolean =
        checkS3Request(url.toString()) ||
                ignoreRequests.requests.any { ignoreRequest ->
                    val path = url.encodedPath
                    val requestMethod = method.toHttpMethod()

                    path.contains(ignoreRequest.path) &&
                            requestMethod == ignoreRequest.method
                }

    private val Response.retryCount: Int
        get() {
            var currentResponse: Response? = this
            var count = 0

            while (currentResponse != null) {
                count++
                currentResponse = currentResponse.priorResponse
            }

            return count
        }

    private fun checkS3Request(url: String): Boolean =
        url.contains(ResourceKeys.IMAGE_URL)

    private companion object {
        const val AUTHORIZATION_HEADER = "authorization"
        const val MAX_AUTH_RETRY_COUNT = 2
    }
}
