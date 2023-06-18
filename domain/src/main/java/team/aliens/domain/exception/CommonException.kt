package team.aliens.domain.exception

sealed class CommonException(
    message: String,
) : RuntimeException() {

    object NoInternet : CommonException("No internet")

    object SignInRequired: CommonException("Sign in required")

    object Unknown : CommonException("Unknown")
}
