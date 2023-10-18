package team.aliens.dms.android.network.point.datasource

import team.aliens.dms.android.network.point.model.FetchPointsResponse
import team.aliens.dms.android.shared.model.PointType

abstract class NetworkPointDataSource {
    abstract suspend fun fetchPoints(
        type: PointType,
        page: Long?,
        size: Long?,
    ): FetchPointsResponse
}
