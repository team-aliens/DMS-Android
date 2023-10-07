package team.aliens.dms_android.core.network.exception

class TooManyRequestsException : NetworkException(
    code = 429,
    message = "Too many requests",
)
