package team.aliens.dms.android.core.network.exception

class UnAuthorizedException : NetworkException(
    code = 401,
    message = "Unauthorized",
)
