package team.aliens.dms_android.core.jwt.exception

import team.aliens.dms.android.core.datastore.exception.TransformFailureException

sealed class CannotStoreTokenExpirationException(message: String?) :
    TransformFailureException(message)

class CannotStoreAccessTokenExpirationException :
    CannotStoreTokenExpirationException("Cannot store access token expiration")

class CannotStoreRefreshTokenExpirationException :
    CannotStoreTokenExpirationException("Cannot store refresh token expiration")
