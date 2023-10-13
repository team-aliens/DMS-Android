package team.aliens.dms.android.domain.exception

sealed class CommonException(
    message: String,
) : RuntimeException() {

    object NoInternet : CommonException("No internet")

    object SignInRequired: CommonException("Sign in required")

    object Unknown : CommonException("Unknown")
}
