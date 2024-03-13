package team.aliens.dms.android.core.network.exception

class ForbiddenException : NetworkException(
    code = 403,
    message = "Forbidden",
)
