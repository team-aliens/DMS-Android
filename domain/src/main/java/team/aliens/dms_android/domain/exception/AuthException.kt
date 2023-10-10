package team.aliens.dms_android.domain.exception

sealed class AuthException(
    message: String,
) : RuntimeException(
    message,
) {
    object PasswordMismatch : team.aliens.dms_android.domain.exception.AuthException("Password mismatch")

    object UserNotFound : team.aliens.dms_android.domain.exception.AuthException("User not found")
}
