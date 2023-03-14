package team.aliens.domain._common.exception

sealed class CommonException(
    message: String,
) : RuntimeException(
    message = message,
) {

    object NoInternet : CommonException(
        message = "No internet",
    )

    object Unknown : CommonException(
        message = "Unknown",
    )
}
