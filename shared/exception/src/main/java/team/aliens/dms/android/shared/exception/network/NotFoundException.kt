package team.aliens.dms.android.shared.exception.network

class NotFoundException : NetworkException(
    code = 404,
    message = "Not found",
)
