package team.aliens.remote.http

import team.aliens.remote.common.HttpMethod

data class Request(
    val method: HttpMethod,
    val path: String,
)
