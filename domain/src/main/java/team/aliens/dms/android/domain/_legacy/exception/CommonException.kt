package team.aliens.dms.android.domain._legacy.exception

sealed class CommonException(
    message: String,
) : RuntimeException() {

    object NoInternet : team.aliens.dms.android.domain._legacy.exception.CommonException("No internet")

    object SignInRequired: team.aliens.dms.android.domain._legacy.exception.CommonException("Sign in required")

    object Unknown : team.aliens.dms.android.domain._legacy.exception.CommonException("Unknown")
}
