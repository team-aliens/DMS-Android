package team.aliens.dms.android.network._legacy.http

import okhttp3.Interceptor
import okhttp3.Response
import team.aliens.dms.android.data.facade.AuthorizationFacade
import team.aliens.dms.android.network.common.HttpPath
import team.aliens.dms.android.network.common.HttpProperty
import team.aliens.dms.android.network._legacy.common.toHttpMethod
import java.util.regex.Pattern
import javax.inject.Inject

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

        return IgnoreRequestWrapper.ignoreRequests.any {
            with(HttpPath.Path) {
                Pattern.compile(
                    it.path.replace(date, dateRegex).replace(noticeId, uuidRegex)
                        .replace(schoolId, uuidRegex).replace(seatId, uuidRegex)
                        .replace(studyRoomId, uuidRegex).replace(remainOptionId, uuidRegex)
                ).matcher(request.path).matches() && it.method == request.method
            }
        }
    }
}

private const val uuidRegex = "(\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12})"

private const val dateRegex = "(\\d{4})-(\\d{2})-(\\d{2})"
