package team.aliens.dms.android.core.jwt.exception

sealed class TokenExpiredException(message: String?) : RuntimeException(message)

class AccessTokenExpiredException : TokenExpiredException("Access token expired")

class RefreshTokenExpiredException : TokenExpiredException("Refresh token expired")
