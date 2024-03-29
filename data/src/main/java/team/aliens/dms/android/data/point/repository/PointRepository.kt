package team.aliens.dms.android.data.point.repository

import team.aliens.dms.android.data.point.model.PointStatus
import team.aliens.dms.android.data.point.model.PointType

abstract class PointRepository {

    abstract suspend fun fetchPoints(
        type: PointType,
        page: Long? = null,
        size: Long? = null,
    ): PointStatus
}
