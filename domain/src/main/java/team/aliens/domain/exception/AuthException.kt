package team.aliens.domain.exception

sealed class AuthException(
    message: String,
    val code: Int,
) : RuntimeException(
    message = message,
) {
    object PasswordMismatch : AuthException(
        message = "Password mismatch",
        code = 401,
    )

    object UserNotFound : AuthException(
        message = "User not found",
        code = 404,
    )
}
