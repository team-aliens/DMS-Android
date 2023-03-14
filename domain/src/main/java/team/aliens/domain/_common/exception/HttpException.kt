package team.aliens.domain._common.exception

sealed class HttpException(
    message: String,
    val code: Int,
) : RuntimeException(
    message,
) {

    object BadRequest : HttpException(
        message = "Bad request",
        code = 400,
    )

    object Unauthorized : HttpException(
        message = "Unauthorized",
        code = 401,
    )

    object Forbidden : HttpException(
        message = "Forbidden",
        code = 403,
    )

    object NotFound : HttpException(
        message = "Not found",
        code = 404,
    )

    object Timeout : HttpException(
        message = "Timeout",
        code = 408,
    )

    object Conflict : HttpException(
        message = "Conflict",
        code = 409,
    )

    object TooManyRequests : HttpException(
        message = "Too many requests",
        code = 429,
    )

    object InternalServerError : HttpException(
        message = "Internal server error",
        code = 500,
    )
}
