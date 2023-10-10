package team.aliens.dms_android.domain.exception

sealed class LocalException(
    message: String,
) : RuntimeException(message) {

    object AccessTokenNotFound : team.aliens.dms_android.domain.exception.LocalException(
        message = "Access token not found",
    )

    object AccessTokenExpiredAtNotFound : team.aliens.dms_android.domain.exception.LocalException(
        message = "Access token expired at not found",
    )

    object RefreshTokenNotFound : team.aliens.dms_android.domain.exception.LocalException(
        message = "Refresh token not found",
    )

    object RefreshTokenExpiredAtNotFound : team.aliens.dms_android.domain.exception.LocalException(
        message = "Refresh token expired at not found",
    )
}
