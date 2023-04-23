package team.aliens.domain._exception

sealed class RemoteException(
    message: String,
    val code: Int,
) : RuntimeException(
    message,
) {

    object BadRequest : RemoteException(
        message = "Bad request",
        code = 400,
    )

    object Unauthorized : RemoteException(
        message = "Unauthorized",
        code = 401,
    )

    object Forbidden : RemoteException(
        message = "Forbidden",
        code = 403,
    )

    object NotFound : RemoteException(
        message = "Not found",
        code = 404,
    )

    object Timeout : RemoteException(
        message = "Timeout",
        code = 408,
    )

    object Conflict : RemoteException(
        message = "Conflict",
        code = 409,
    )

    object TooManyRequests : RemoteException(
        message = "Too many requests",
        code = 429,
    )

    object InternalServerError : RemoteException(
        message = "Internal server error",
        code = 500,
    )
}
