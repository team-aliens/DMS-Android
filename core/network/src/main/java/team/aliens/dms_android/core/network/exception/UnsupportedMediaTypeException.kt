package team.aliens.dms_android.core.network.exception

class UnsupportedMediaTypeException : NetworkException(
    code = 415,
    message = "Unsupported media type",
)
