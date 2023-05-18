package team.aliens.domain.repository

import team.aliens.domain.model.point.FetchPointsInput
import team.aliens.domain.model.point.FetchPointsOutput

interface PointRepository {

    suspend fun fetchPoints(
        input: FetchPointsInput,
    ): FetchPointsOutput
}
