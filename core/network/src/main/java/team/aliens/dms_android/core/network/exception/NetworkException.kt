package team.aliens.dms_android.core.network.exception

sealed class NetworkException(
    // TODO: consider default value
    val code: Int = NO_CODE,
    message: String?,
) : RuntimeException(message)

const val NO_CODE = -1
