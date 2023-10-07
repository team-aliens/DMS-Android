package team.aliens.dms_android.core.network

enum class HttpMethod {
    POST, GET, UPDATE, PUT, PATCH, DELETE,
    ;
}

fun String.toHttpMethod(): HttpMethod = HttpMethod.valueOf(this)
