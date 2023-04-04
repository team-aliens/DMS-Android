package team.aliens.remote.http

import okhttp3.Interceptor
import okhttp3.Response
import team.aliens.data._facade.AuthorizationFacade
import team.aliens.remote.common.toHttpMethod

class AuthorizationInterceptor(
    private val authorizationFacade: AuthorizationFacade,
    private val tokenReissueClient: TokenReissueClient,
    private val ignoreRequestWrapper: IgnoreRequestWrapper,
) : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain,
    ): Response {

        val interceptedRequest = chain.request()

        val request = Request(
            method = interceptedRequest.method.toHttpMethod(),
            path = interceptedRequest.url.encodedPath,
        )


        /*try { TODO implement ignore path handling
            handleIgnorePath(
                method = method,
                path = path,
            )
        } catch (e: Exception) {
            chain.proceed(request)
        }*/

        /*val response = try {

        }*/

        return chain.proceed(interceptedRequest)
    }

    /*private fun handleIgnorePath(
        method: HttpMethod,
        path: String,
    ): Nothing? {
        return run {
            if () throw
            else
        }
    }*/

    /*private fun reissueTokenOrNull() : Response? {
        // TODO reissue token, or return null
        return TODO()
    }*/
}
