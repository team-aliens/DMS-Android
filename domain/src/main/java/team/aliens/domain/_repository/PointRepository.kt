package team.aliens.domain._repository

import team.aliens.domain._model._common.PointType
import team.aliens.domain._model.point.FetchPointsOutput

interface PointRepository {

    suspend fun fetchPoints(
        type: PointType,
        page: Long? = null,
        size: Long? = null,
    ): FetchPointsOutput
}
