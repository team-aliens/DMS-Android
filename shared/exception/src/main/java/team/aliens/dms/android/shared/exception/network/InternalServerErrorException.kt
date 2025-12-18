package team.aliens.dms.android.shared.exception.network

class InternalServerErrorException : NetworkException(
    code = 500,
    message = "Internal server error",
)
