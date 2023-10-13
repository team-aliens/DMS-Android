package team.aliens.dms.android.data.datasource.remote

import team.aliens.dms.android.domain.model.point.FetchPointsInput
import team.aliens.dms.android.domain.model.point.FetchPointsOutput

interface RemotePointDataSource {

    suspend fun fetchPoints(
        input: FetchPointsInput,
    ): FetchPointsOutput
}
