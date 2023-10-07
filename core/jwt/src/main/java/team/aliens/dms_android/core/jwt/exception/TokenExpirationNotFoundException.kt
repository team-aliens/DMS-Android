package team.aliens.dms_android.core.jwt.exception

sealed class TokenExpirationNotFoundException(message: String?) : RuntimeException(message)

class AccessTokenExpirationNotFoundException :
    TokenExpirationNotFoundException("Access token expiration not found")

class RefreshTokenExpirationNotFoundException :
    TokenExpirationNotFoundException("Refresh token expiration not found")
