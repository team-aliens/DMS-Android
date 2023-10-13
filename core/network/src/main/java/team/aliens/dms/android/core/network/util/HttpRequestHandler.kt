package team.aliens.dms.android.core.network.util

import team.aliens.dms.android.core.network.exception.BadRequestException
import team.aliens.dms.android.core.network.exception.ConflictException
import team.aliens.dms.android.core.network.exception.ForbiddenException
import team.aliens.dms.android.core.network.exception.InternalServerError
import team.aliens.dms.android.core.network.exception.NotFoundException
import team.aliens.dms.android.core.network.exception.RequestTimeoutException
import team.aliens.dms.android.core.network.exception.TooManyRequestsException
import team.aliens.dms.android.core.network.exception.UnAuthorizedException
import team.aliens.dms.android.core.network.exception.UnknownException
import team.aliens.dms.android.core.network.exception.UnsupportedMediaTypeException

suspend inline fun <T> sendHttpRequest(
    on400: (message: String?) -> Nothing = { throw BadRequestException() },
    on401: (message: String?) -> Nothing = { throw UnAuthorizedException() },
    on403: (message: String?) -> Nothing = { throw ForbiddenException() },
    on404: (message: String?) -> Nothing = { throw NotFoundException() },
    on408: (message: String?) -> Nothing = { throw RequestTimeoutException() },
    on409: (message: String?) -> Nothing = { throw ConflictException() },
    on415: (message: String?) -> Nothing = { throw UnsupportedMediaTypeException() },
    on429: (message: String?) -> Nothing = { throw TooManyRequestsException() },
    on500: (message: String?) -> Nothing = { throw InternalServerError() },
    onUnknownException: (message: String) -> Nothing = { throw UnknownException() },
    crossinline httpRequest: suspend () -> T,
): T = try {
    httpRequest()
} catch (httpException: retrofit2.HttpException) {

    val code = httpException.code()
    val message = httpException.message()

    require(code in 400 until 600) {
        "Http status code must be in 400 until 600"
    }

    when (code) {
        400 -> on400(message)
        401 -> on401(message)
        403 -> on403(message)
        404 -> on404(message)
        408 -> on408(message)
        409 -> on409(message)
        415 -> on415(message)
        429 -> on429(message)
        in 400 until 500 -> onUnknownException(message)
        500 -> on500(message)
        else -> onUnknownException(message)
    }
}
