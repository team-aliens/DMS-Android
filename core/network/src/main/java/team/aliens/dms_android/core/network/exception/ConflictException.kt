package team.aliens.dms_android.core.network.exception

class ConflictException : NetworkException(
    code = 409,
    message = "Conflict",
)
