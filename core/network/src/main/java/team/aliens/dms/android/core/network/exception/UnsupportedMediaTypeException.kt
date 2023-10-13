package team.aliens.dms.android.core.network.exception

class UnsupportedMediaTypeException : NetworkException(
    code = 415,
    message = "Unsupported media type",
)
