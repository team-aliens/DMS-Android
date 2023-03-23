package team.aliens.domain._exception

sealed class CommonException(
    message: String,
) : RuntimeException() {

    object NoInternet : CommonException(
        message = "No internet",
    )

    object Unknown : CommonException(
        message = "Unknown",
    )
}
