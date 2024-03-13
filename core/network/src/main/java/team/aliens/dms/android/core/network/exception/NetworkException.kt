package team.aliens.dms.android.core.network.exception

sealed class NetworkException(
    val code: Int = NO_CODE,
    message: String?,
) : RuntimeException(message)

const val NO_CODE = -1
