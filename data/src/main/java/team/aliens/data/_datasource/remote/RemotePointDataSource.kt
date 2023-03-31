package team.aliens.data._datasource.remote

import team.aliens.domain._model._common.PointType
import team.aliens.domain._model.point.FetchPointsOutput

interface RemotePointDataSource {

    suspend fun fetchPoints(
        type: PointType,
        page: Long? = null,
        size: Long? = null,
    ): FetchPointsOutput
}
