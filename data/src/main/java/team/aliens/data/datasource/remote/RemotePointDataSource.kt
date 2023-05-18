package team.aliens.data.datasource.remote

import team.aliens.domain.model.point.FetchPointsInput
import team.aliens.domain.model.point.FetchPointsOutput

interface RemotePointDataSource {

    suspend fun fetchPoints(
        input: FetchPointsInput,
    ): FetchPointsOutput
}
