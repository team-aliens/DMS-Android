package team.aliens.dms_android.core.jwt.exception

import team.aliens.dms.android.core.datastore.exception.TransformFailureException

sealed class CannotStoreTokenException(message: String?) : TransformFailureException(message)

class CannotStoreAccessTokenException : CannotStoreTokenException("Cannot store access token")

class CannotStoreRefreshTokenException : CannotStoreTokenException("Cannot store refresh token")
