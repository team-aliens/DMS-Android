package team.aliens.dms.android.domain.exception

sealed class RemoteException(
    message: String,
) : RuntimeException(message) {

    object BadRequest : RemoteException("Bad request")

    object Unauthorized : RemoteException("Unauthorized")

    object NoPermission : RemoteException("No permission")

    object InvalidToken : RemoteException("Invalid token")

    object TokenExpired : RemoteException("Token expired")

    object Forbidden : RemoteException("Forbidden")

    object NotFound : RemoteException("Not found")

    object Timeout : RemoteException("Timeout")

    object Conflict : RemoteException("Conflict")

    object TooManyRequests : RemoteException("Too many requests")

    object InternalServerError : RemoteException("Internal server error")
}
