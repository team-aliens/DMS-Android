package team.aliens.dms.android.domain.exception

sealed class AuthException(
    message: String,
) : RuntimeException(
    message,
) {
    object PasswordMismatch : AuthException("Password mismatch")

    object UserNotFound : AuthException("User not found")
}
