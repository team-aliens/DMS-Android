package team.aliens.dms.android.network._legacy.util
/*
import team.aliens.dms.android.domain.exception.CommonException
import team.aliens.dms.android.domain.exception.RemoteException

@Suppress("UNREACHABLE_CODE")
suspend inline fun <T> sendHttpRequest(
    onBadRequest: (message: String) -> Nothing = { throw RemoteException.BadRequest },
    onUnauthorized: (message: String) -> Nothing = { throw RemoteException.Unauthorized },
    onForbidden: (message: String) -> Nothing = { throw RemoteException.Forbidden },
    onNotFound: (message: String) -> Nothing = { throw RemoteException.NotFound },
    onTimeout: (message: String) -> Nothing = { throw RemoteException.Timeout },
    onConflict: (message: String) -> Nothing = { throw RemoteException.Conflict },
    onTooManyRequest: (message: String) -> Nothing = { throw RemoteException.TooManyRequests },
    onInternalServerError: (message: String) -> Nothing = { throw RemoteException.InternalServerError },
    onUnknownException: (message: String) -> Nothing = { throw CommonException.Unknown },
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
}*/
