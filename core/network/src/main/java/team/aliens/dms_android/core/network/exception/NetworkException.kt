package team.aliens.dms_android.core.network.exception

sealed class RemoteException(
    message: String?,
) : RuntimeException(message)
