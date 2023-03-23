package team.aliens.data._repository

import team.aliens.domain._model.point.FetchPointsOutput
import team.aliens.domain._repository.PointRepository

class PointRepositoryImpl(
    // private val remotePointDataSource: RemotePointDataSource
) : PointRepository {

    override suspend fun fetchPoints(): FetchPointsOutput {
        TODO("Not yet implemented")
    }
}