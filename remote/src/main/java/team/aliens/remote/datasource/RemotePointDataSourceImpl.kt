package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemotePointDataSource
import team.aliens.domain._model._common.PointType
import team.aliens.domain._model.point.FetchPointsOutput
import team.aliens.remote.model.point.toDomain
import team.aliens.remote.service.PointService
import team.aliens.remote.util.sendHttpRequest
import javax.inject.Inject

class RemotePointDataSourceImpl @Inject constructor(
    private val pointService: PointService,
) : RemotePointDataSource {
    override suspend fun fetchPoints(
        type: PointType,
        page: Long?,
        size: Long?,
    ): FetchPointsOutput {
        return sendHttpRequest {
            pointService.fetchPoints(
                type = type.name,
                page = page,
                size = size,
            )
        }.toDomain()
    }
}
