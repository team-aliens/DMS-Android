package team.aliens.dms.android.shared.exception.network

class BadRequestException : NetworkException(
    code = 400,
    message = "Bad request",
)
