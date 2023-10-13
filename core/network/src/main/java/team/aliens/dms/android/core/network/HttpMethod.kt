package team.aliens.dms.android.core.network

enum class HttpMethod {
    POST, GET, UPDATE, PUT, PATCH, DELETE,
    ;
}

fun String.toHttpMethod(): HttpMethod = HttpMethod.valueOf(this)
