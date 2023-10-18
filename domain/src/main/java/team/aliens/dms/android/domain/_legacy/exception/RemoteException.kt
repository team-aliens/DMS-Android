package team.aliens.dms.android.domain._legacy.exception

sealed class RemoteException(
    message: String,
) : RuntimeException(message) {

    object BadRequest : team.aliens.dms.android.domain._legacy.exception.RemoteException("Bad request")

    object Unauthorized : team.aliens.dms.android.domain._legacy.exception.RemoteException("Unauthorized")

    object NoPermission : team.aliens.dms.android.domain._legacy.exception.RemoteException("No permission")

    object InvalidToken : team.aliens.dms.android.domain._legacy.exception.RemoteException("Invalid token")

    object TokenExpired : team.aliens.dms.android.domain._legacy.exception.RemoteException("Token expired")

    object Forbidden : team.aliens.dms.android.domain._legacy.exception.RemoteException("Forbidden")

    object NotFound : team.aliens.dms.android.domain._legacy.exception.RemoteException("Not found")

    object Timeout : team.aliens.dms.android.domain._legacy.exception.RemoteException("Timeout")

    object Conflict : team.aliens.dms.android.domain._legacy.exception.RemoteException("Conflict")

    object TooManyRequests : team.aliens.dms.android.domain._legacy.exception.RemoteException("Too many requests")

    object InternalServerError : team.aliens.dms.android.domain._legacy.exception.RemoteException("Internal server error")
}
