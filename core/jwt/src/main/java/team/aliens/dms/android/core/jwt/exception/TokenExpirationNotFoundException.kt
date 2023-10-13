package team.aliens.dms.android.core.jwt.exception

import team.aliens.dms.android.core.datastore.exception.SearchFailureException

sealed class TokenExpirationNotFoundException(message: String?) : SearchFailureException(message)

class AccessTokenExpirationNotFoundException :
    TokenExpirationNotFoundException("Access token expiration not found")

class RefreshTokenExpirationNotFoundException :
    TokenExpirationNotFoundException("Refresh token expiration not found")
