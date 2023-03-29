package team.aliens.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import team.aliens.remote.extension.toHttpMethod

class AuthInterceptor(
    // TODO local storage source injection
    // TODO token reissue client injection
) : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain,
    ): Response {

        val request = chain.request()

        val method = request.method.toHttpMethod()
        val path = request.url.encodedPath

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

        return chain.proceed(request)
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
