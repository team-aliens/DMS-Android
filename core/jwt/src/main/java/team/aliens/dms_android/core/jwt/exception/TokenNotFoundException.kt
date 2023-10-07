package team.aliens.dms_android.core.jwt.exception

sealed class TokenNotFoundException(message: String?) : RuntimeException(message)

class AccessTokenNotFoundException : TokenNotFoundException("Access token not found")

class RefreshTokenNotFoundException : TokenNotFoundException("Refresh token not found")
