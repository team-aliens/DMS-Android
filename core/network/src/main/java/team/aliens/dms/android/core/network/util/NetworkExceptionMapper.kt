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

suspend inline fun <T> statusMapping(
    onBadRequest: () -> Nothing = { throw BadRequestException() },
    onUnauthorized: () -> Nothing = { throw UnAuthorizedException() },
    onForbidden: () -> Nothing = { throw ForbiddenException() },
    onNotFound: () -> Nothing = { throw NotFoundException() },
    onRequestTimeout: () -> Nothing = { throw RequestTimeoutException() },
    onConflict: () -> Nothing = { throw ConflictException() },
    onUnsupportedMediaType: () -> Nothing = { throw UnsupportedMediaTypeException() },
    onTooManyRequests: () -> Nothing = { throw TooManyRequestsException() },
    onInternalServerError: () -> Nothing = { throw InternalServerError() },
    onUnknownException: () -> Nothing = { throw UnknownException() },
    crossinline block: suspend () -> T,
): T = try {
    block()
} catch (e: Exception) {
    when (e) {
        is BadRequestException -> onBadRequest()
        is UnAuthorizedException -> onUnauthorized()
        is ForbiddenException -> onForbidden()
        is NotFoundException -> onNotFound()
        is RequestTimeoutException -> onRequestTimeout()
        is ConflictException -> onConflict()
        is UnsupportedMediaTypeException -> onUnsupportedMediaType()
        is TooManyRequestsException -> onTooManyRequests()
        is InternalServerError -> onInternalServerError()
        else -> onUnknownException()
    }
}
