package team.aliens.data.repository

import team.aliens.data.datasource.remote.RemotePointDataSource
import team.aliens.domain._model.point.FetchPointsInput
import team.aliens.domain._model.point.FetchPointsOutput
import team.aliens.domain._repository.PointRepository
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(
    private val remotePointDataSource: RemotePointDataSource,
) : PointRepository {

    override suspend fun fetchPoints(
        input: FetchPointsInput,
    ): FetchPointsOutput {
        return remotePointDataSource.fetchPoints(
            input = input,
        )
    }
}