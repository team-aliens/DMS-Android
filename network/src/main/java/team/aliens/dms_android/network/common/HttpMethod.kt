package team.aliens.dms_android.network.common

enum class HttpMethod {
    POST, GET, UPDATE, PUT, PATCH, DELETE,
    ;
}

internal fun String.toHttpMethod(): HttpMethod = HttpMethod.valueOf(this)
