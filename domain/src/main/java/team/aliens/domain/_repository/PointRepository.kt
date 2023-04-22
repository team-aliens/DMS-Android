package team.aliens.domain._repository

import team.aliens.domain._model.point.FetchPointsInput
import team.aliens.domain._model.point.FetchPointsOutput

interface PointRepository {

    suspend fun fetchPoints(
        input: FetchPointsInput,
    ): FetchPointsOutput
}
