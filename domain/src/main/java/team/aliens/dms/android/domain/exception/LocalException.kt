package team.aliens.dms.android.domain.exception

sealed class LocalException(
    message: String,
) : RuntimeException(message) {

    object AccessTokenNotFound : LocalException(
        message = "Access token not found",
    )

    object AccessTokenExpiredAtNotFound : LocalException(
        message = "Access token expired at not found",
    )

    object RefreshTokenNotFound : LocalException(
        message = "Refresh token not found",
    )

    object RefreshTokenExpiredAtNotFound : LocalException(
        message = "Refresh token expired at not found",
    )
}
