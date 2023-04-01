package team.aliens.data._repository

import team.aliens.data._datasource.remote.RemotePointDataSource
import team.aliens.domain._model._common.PointType
import team.aliens.domain._model.point.FetchPointsOutput
import team.aliens.domain._repository.PointRepository
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(
    private val remotePointDataSource: RemotePointDataSource,
) : PointRepository {

    override suspend fun fetchPoints(
        type: PointType,
        page: Long?,
        size: Long?,
    ): FetchPointsOutput {
        return remotePointDataSource.fetchPoints(
            type = type,
            page = page,
            size = size,
        )
    }
}