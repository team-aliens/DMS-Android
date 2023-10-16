package team.aliens.dms.android.network._legacy.http

import team.aliens.dms.android.network._legacy.common.HttpMethod

data class Request(
    val method: team.aliens.dms.android.network._legacy.common.HttpMethod,
    val path: String,
)
