package team.aliens.remote.common

enum class HttpMethod {
    POST, GET, UPDATE, PUT, PATCH, DELETE,
    ;
}

internal fun String.toHttpMethod(): HttpMethod = HttpMethod.valueOf(this)
