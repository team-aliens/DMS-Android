package team.aliens.domain._repository

import team.aliens.domain._model.point.QueryPointsResult

interface PointRepository {

    suspend fun queryPoints(): QueryPointsResult
}
