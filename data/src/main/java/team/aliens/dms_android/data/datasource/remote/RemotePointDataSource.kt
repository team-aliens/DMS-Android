package team.aliens.dms_android.data.datasource.remote

import team.aliens.dms_android.domain.model.point.FetchPointsInput
import team.aliens.dms_android.domain.model.point.FetchPointsOutput

interface RemotePointDataSource {

    suspend fun fetchPoints(
        input: FetchPointsInput,
    ): FetchPointsOutput
}
