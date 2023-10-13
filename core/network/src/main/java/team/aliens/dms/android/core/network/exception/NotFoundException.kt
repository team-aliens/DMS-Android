package team.aliens.dms.android.core.network.exception

class NotFoundException : NetworkException(
    code = 404,
    message = "Not found",
)
