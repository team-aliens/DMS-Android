package team.aliens.dms.android.core.network.util

import team.aliens.dms.android.core.network.exception.BadRequestException
import team.aliens.dms.android.core.network.exception.ConflictException
import team.aliens.dms.android.core.network.exception.ForbiddenException
import team.aliens.dms.android.core.network.exception.InternalServerError
import team.aliens.dms.android.core.network.exception.NotFoundException
import team.aliens.dms.android.core.network.exception.RequestTimeoutException
import team.aliens.dms.android.core.network.exception.TooManyRequestsException
import team.aliens.dms.android.core.network.exception.UnAuthorizedException
import team.aliens.dms.android.core.network.exception.UnsupportedMediaTypeException
import team.aliens.dms.android.shared.exception.NotDefinedException

suspend inline fun <T> statusMapping(
    onBadRequest: () -> Nothing = { throw NotDefinedException() },
    onUnauthorized: () -> Nothing = { throw NotDefinedException() },
    onForbidden: () -> Nothing = { throw NotDefinedException() },
    onNotFound: () -> Nothing = { throw NotDefinedException() },
    onRequestTimeout: () -> Nothing = { throw NotDefinedException() },
    onConflict: () -> Nothing = { throw NotDefinedException() },
    onUnsupportedMediaType: () -> Nothing = { throw NotDefinedException() },
    onTooManyRequests: () -> Nothing = { throw NotDefinedException() },
    onInternalServerError: () -> Nothing = { throw NotDefinedException() },
    onUnknownException: () -> Nothing = { throw NotDefinedException() },
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
