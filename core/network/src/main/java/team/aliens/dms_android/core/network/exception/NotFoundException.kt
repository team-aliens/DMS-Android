package team.aliens.dms_android.core.network.exception

class NotFoundException : NetworkException(
    code = 404,
    message = "Not found",
)
