package team.aliens.dms.android.data.point.repository

import team.aliens.dms.android.data.point.model.FetchPointsOutput
import team.aliens.dms.android.data.point.model.PointType

abstract class PointRepository {

    abstract fun fetchPoints(
        type: PointType,
        page: Long? = null,
        size: Long? = null,
    ): FetchPointsOutput
}
