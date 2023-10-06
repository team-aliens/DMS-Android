package team.aliens.dms_android.core.network.exception

sealed class NetworkException(
    // TODO: consider default value
    val code: Int,
    message: String?,
) : RuntimeException(message)
