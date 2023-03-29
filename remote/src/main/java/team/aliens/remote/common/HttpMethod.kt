package team.aliens.remote.common

internal enum class HttpMethod {
    POST, GET, UPDATE, DELETE,
    ;
}

internal fun String.toHttpMethod(): HttpMethod = HttpMethod.valueOf(this)
