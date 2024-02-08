package team.aliens.dms.android.core.jwt.exception

sealed class CannotUseTokenException(message: String?) : RuntimeException(message)

class CannotUseAccessTokenException : CannotUseTokenException("Cannot use access token")

class CannotUseRefreshTokenException : CannotUseTokenException("Cannot use refresh token")
