package team.aliens.dms.android.network.http

import team.aliens.dms.android.network.common.HttpMethod

data class Request(
    val method: HttpMethod,
    val path: String,
)
