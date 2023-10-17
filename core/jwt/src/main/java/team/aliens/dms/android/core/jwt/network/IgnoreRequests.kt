package team.aliens.dms.android.core.jwt.network

import team.aliens.dms.android.core.network.HttpRequest

interface IgnoreRequests {
    val requests: List<HttpRequest>
}
