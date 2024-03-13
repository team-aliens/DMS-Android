package team.aliens.dms.android.core.network.exception

class RequestTimeoutException : NetworkException(
    code = 408,
    message = "Request timeout",
)
