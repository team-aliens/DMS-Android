package team.aliens.dms.android.network.point.datasource

import team.aliens.dms.android.network.point.model.FetchPointsResponse

abstract class NetworkPointDataSource {
    abstract suspend fun fetchPoints(
        type: String,
        page: Long?,
        size: Long?,
    ): FetchPointsResponse
}
