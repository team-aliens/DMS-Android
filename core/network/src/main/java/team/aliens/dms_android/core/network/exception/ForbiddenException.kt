package team.aliens.dms_android.core.network.exception

class ForbiddenException : NetworkException(
    code = 403,
    message = "Forbidden",
)
