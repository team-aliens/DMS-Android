package team.aliens.dms.android.core.jwt.exception

sealed class TokenReissueException(message: String?) : RuntimeException(message)

class CannotReissueTokenException : TokenReissueException("Cannot reissue token")
