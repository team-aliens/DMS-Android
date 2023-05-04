package team.aliens.domain._exception

sealed class CommonException(
    message: String,
) : RuntimeException() {

    object NoInternet : CommonException(
        message = "No internet",
    )

    object SignInRequired: CommonException(
        message = "Sign in required"
    )

    object Unknown : CommonException(
        message = "Unknown",
    )
}
