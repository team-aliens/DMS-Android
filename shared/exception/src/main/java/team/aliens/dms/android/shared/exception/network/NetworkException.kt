package team.aliens.dms.android.shared.exception.network

sealed class NetworkException( // TODO :: 문제 없으면 abstract로 변경
    val code: Int = NO_CODE,
    message: String?,
) : RuntimeException(message)

const val NO_CODE = -1
