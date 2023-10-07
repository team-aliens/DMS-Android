package team.aliens.dms_android.network.http

import team.aliens.network.common.HttpMethod

data class Request(
    val method: HttpMethod,
    val path: String,
)
