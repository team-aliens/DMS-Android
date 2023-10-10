package team.aliens.dms_android.domain.exception

sealed class CommonException(
    message: String,
) : RuntimeException() {

    object NoInternet : team.aliens.dms_android.domain.exception.CommonException("No internet")

    object SignInRequired: team.aliens.dms_android.domain.exception.CommonException("Sign in required")

    object Unknown : team.aliens.dms_android.domain.exception.CommonException("Unknown")
}
