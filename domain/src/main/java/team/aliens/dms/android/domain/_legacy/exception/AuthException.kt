package team.aliens.dms.android.domain._legacy.exception

sealed class AuthException(
    message: String,
) : RuntimeException(
    message,
) {
    object PasswordMismatch : team.aliens.dms.android.domain._legacy.exception.AuthException("Password mismatch")

    object UserNotFound : team.aliens.dms.android.domain._legacy.exception.AuthException("User not found")
}
