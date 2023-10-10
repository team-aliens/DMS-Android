package team.aliens.dms_android.data.repository

import team.aliens.dms_android.data.datasource.remote.RemotePointDataSource
import team.aliens.dms_android.domain.model.point.FetchPointsInput
import team.aliens.dms_android.domain.model.point.FetchPointsOutput
import team.aliens.dms_android.domain.repository.PointRepository
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