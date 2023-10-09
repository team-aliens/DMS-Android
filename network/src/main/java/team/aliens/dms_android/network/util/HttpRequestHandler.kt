package team.aliens.dms_android.network.util

import team.aliens.dms_android.domain.exception.CommonException
import team.aliens.dms_android.domain.exception.RemoteException

@Suppress("UNREACHABLE_CODE")
suspend inline fun <T> sendHttpRequest(
    onBadRequest: (message: String) -> Nothing = { throw team.aliens.dms_android.domain.exception.RemoteException.BadRequest },
    onUnauthorized: (message: String) -> Nothing = { throw team.aliens.dms_android.domain.exception.RemoteException.Unauthorized },
    onForbidden: (message: String) -> Nothing = { throw team.aliens.dms_android.domain.exception.RemoteException.Forbidden },
    onNotFound: (message: String) -> Nothing = { throw team.aliens.dms_android.domain.exception.RemoteException.NotFound },
    onTimeout: (message: String) -> Nothing = { throw team.aliens.dms_android.domain.exception.RemoteException.Timeout },
    onConflict: (message: String) -> Nothing = { throw team.aliens.dms_android.domain.exception.RemoteException.Conflict },
    onTooManyRequest: (message: String) -> Nothing = { throw team.aliens.dms_android.domain.exception.RemoteException.TooManyRequests },
    onInternalServerError: (message: String) -> Nothing = { throw team.aliens.dms_android.domain.exception.RemoteException.InternalServerError },
    onUnknownException: (message: String) -> Nothing = { throw team.aliens.dms_android.domain.exception.CommonException.Unknown },
    crossinline httpRequest: suspend () -> T,
): T {
    return try {
        httpRequest()
    } catch (
        httpException: retrofit2.HttpException,
    ) {

        val httpStatusCode = httpException.code()
        val message = httpException.message()

        require(httpStatusCode in 400 until 600)

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
