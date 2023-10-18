package team.aliens.dms.android.domain._legacy.repository

import team.aliens.dms.android.domain.model.point.FetchPointsInput
import team.aliens.dms.android.domain.model.point.FetchPointsOutput

interface PointRepository {

    suspend fun fetchPoints(
        input: FetchPointsInput,
    ): FetchPointsOutput
}
