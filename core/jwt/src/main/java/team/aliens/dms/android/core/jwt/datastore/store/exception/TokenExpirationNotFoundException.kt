package team.aliens.dms.android.core.jwt.datastore.store.exception

import team.aliens.dms.android.core.datastore.exception.LoadFailureException

sealed class TokenExpirationNotFoundException(message: String?) : LoadFailureException(message)

class AccessTokenExpirationNotFoundException :
    TokenExpirationNotFoundException("Access token expiration not found")

class RefreshTokenExpirationNotFoundException :
    TokenExpirationNotFoundException("Refresh token expiration not found")
