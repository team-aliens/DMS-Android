package team.aliens.dms_android.core.network.exception

class UnAuthorizedException : NetworkException(
    code = 401,
    message = "Unauthorized",
)
