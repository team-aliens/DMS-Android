package team.aliens.data._datasource.remote

import team.aliens.domain._model.point.FetchPointsInput
import team.aliens.domain._model.point.FetchPointsOutput

interface RemotePointDataSource {

    suspend fun fetchPoints(
        input: FetchPointsInput,
    ): FetchPointsOutput
}
