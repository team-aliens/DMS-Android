package team.aliens.dms_android.domain.exception

sealed class RemoteException(
    message: String,
) : RuntimeException(message) {

    object BadRequest : team.aliens.dms_android.domain.exception.RemoteException("Bad request")

    object Unauthorized : team.aliens.dms_android.domain.exception.RemoteException("Unauthorized")

    object NoPermission : team.aliens.dms_android.domain.exception.RemoteException("No permission")

    object InvalidToken : team.aliens.dms_android.domain.exception.RemoteException("Invalid token")

    object TokenExpired : team.aliens.dms_android.domain.exception.RemoteException("Token expired")

    object Forbidden : team.aliens.dms_android.domain.exception.RemoteException("Forbidden")

    object NotFound : team.aliens.dms_android.domain.exception.RemoteException("Not found")

    object Timeout : team.aliens.dms_android.domain.exception.RemoteException("Timeout")

    object Conflict : team.aliens.dms_android.domain.exception.RemoteException("Conflict")

    object TooManyRequests : team.aliens.dms_android.domain.exception.RemoteException("Too many requests")

    object InternalServerError : team.aliens.dms_android.domain.exception.RemoteException("Internal server error")
}
