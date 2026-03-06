package team.aliens.dms.android.core.jwt.network.exception

sealed class TokenReissueException(message: String?) : RuntimeException(message)

class CannotReissueTokenException(
    val statusCode: Int,
) : TokenReissueException("Cannot reissue token")
