package team.aliens.dms.android.core.network.exception

class InternalServerError : NetworkException(
    code = 500,
    message = "Internal server error",
)
