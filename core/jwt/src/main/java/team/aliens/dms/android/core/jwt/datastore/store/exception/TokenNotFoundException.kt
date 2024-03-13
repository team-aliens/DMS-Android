package team.aliens.dms.android.core.jwt.datastore.store.exception

import team.aliens.dms.android.core.datastore.exception.LoadFailureException

sealed class TokenNotFoundException(message: String?) : LoadFailureException(message)

class AccessTokenNotFoundException : TokenNotFoundException("Access token not found")

class RefreshTokenNotFoundException : TokenNotFoundException("Refresh token not found")
