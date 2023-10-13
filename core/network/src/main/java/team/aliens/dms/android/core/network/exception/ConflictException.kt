package team.aliens.dms.android.core.network.exception

class ConflictException : NetworkException(
    code = 409,
    message = "Conflict",
)
