package team.aliens.dms_android.core.network.exception

class BadRequestException : NetworkException(
    code = 400,
    message = "Bad request",
)
