package team.aliens.dms_android.domain.repository

import team.aliens.dms_android.domain.model.point.FetchPointsInput
import team.aliens.dms_android.domain.model.point.FetchPointsOutput

interface PointRepository {

    suspend fun fetchPoints(
        input: FetchPointsInput,
    ): FetchPointsOutput
}
