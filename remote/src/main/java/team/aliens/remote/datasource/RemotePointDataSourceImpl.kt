package team.aliens.remote.datasource

import team.aliens.data.datasource.remote.RemotePointDataSource
import team.aliens.domain._model.point.FetchPointsInput
import team.aliens.domain._model.point.FetchPointsOutput
import team.aliens.remote.model.point.toDomain
import team.aliens.remote.apiservice.PointApiService
import team.aliens.remote.util.sendHttpRequest
import javax.inject.Inject

class RemotePointDataSourceImpl @Inject constructor(
    private val pointApiService: PointApiService,
) : RemotePointDataSource {
    override suspend fun fetchPoints(
        input: FetchPointsInput,
    ): FetchPointsOutput {
        return sendHttpRequest {
            pointApiService.fetchPoints(
                type = input.type.name,
                page = input.page,
                size = input.size,
            )
        }.toDomain()
    }
}
