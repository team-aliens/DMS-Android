package team.aliens.dms.android.network._legacy.common

enum class HttpMethod {
    POST, GET, UPDATE, PUT, PATCH, DELETE,
    ;
}

internal fun String.toHttpMethod(): team.aliens.dms.android.network._legacy.common.HttpMethod = team.aliens.dms.android.network._legacy.common.HttpMethod.valueOf(this)
