package team.aliens.remote.util

import team.aliens.domain._exception.CommonException
import team.aliens.domain._exception.HttpException

@Suppress("UNREACHABLE_CODE")
suspend fun <T> sendHttpRequest(
    onBadRequest: (message: String) -> Nothing = { throw HttpException.BadRequest },
    onUnauthorized: (message: String) -> Nothing = { throw HttpException.Unauthorized },
    onForbidden: (message: String) -> Nothing = { throw HttpException.Forbidden },
    onNotFound: (message: String) -> Nothing = { throw HttpException.NotFound },
    onTimeout: (message: String) -> Nothing = { throw HttpException.Timeout },
    onConflict: (message: String) -> Nothing = { throw HttpException.Conflict },
    onTooManyRequest: (message: String) -> Nothing = { throw HttpException.TooManyRequests },
    onInternalServerError: (message: String) -> Nothing = { throw HttpException.InternalServerError },
    onUnknownException: (message: String) -> Nothing = { throw CommonException.Unknown },
    handleByHttpStatusCode: ((code: Int) -> Any)? = null,
    httpRequest: suspend () -> T,
): T {
    return try {
        httpRequest()
    } catch (
        httpException: retrofit2.HttpException,
    ) {

        val httpStatusCode = httpException.code()
        val message = httpException.message()

        require(httpStatusCode in 400 until 600)

        if (handleByHttpStatusCode != null)
            handleByHttpStatusCode(httpStatusCode)

        throw when (httpStatusCode) {
            400 -> onBadRequest(message)
            401 -> onUnauthorized(message)
            403 -> onForbidden(message)
            404 -> onNotFound(message)
            408 -> onTimeout(message)
            409 -> onConflict(message)
            429 -> onTooManyRequest(message)
            in 500 until 600 -> onInternalServerError(message)
            else -> onUnknownException(message)
        }
    }
}
