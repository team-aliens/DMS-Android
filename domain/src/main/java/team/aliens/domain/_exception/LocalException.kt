package team.aliens.domain._exception

sealed class LocalException(
    message: String,
) : RuntimeException(
    message,
) {

    object AccessTokenNotFound : LocalException(
        message = "Access Token Not Found",
    )

    object AccessTokenExpiredNotFound : LocalException(
        message = "Access Token Expired At Not Found",
    )

    object RefreshTokenNotFound : LocalException(
        message = "Refresh Token Not Found",
    )

    object RefreshTokenExpiredAtNotFound : LocalException(
        message = "Refresh Token Expired At Not Found",
    )
}
