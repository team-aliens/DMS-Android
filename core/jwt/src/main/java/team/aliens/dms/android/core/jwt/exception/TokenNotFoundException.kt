package team.aliens.dms.android.core.jwt.exception

import team.aliens.dms.android.core.datastore.exception.SearchFailureException

sealed class TokenNotFoundException(message: String?) : SearchFailureException(message)

class AccessTokenNotFoundException : TokenNotFoundException("Access token not found")

class RefreshTokenNotFoundException : TokenNotFoundException("Refresh token not found")
