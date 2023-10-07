package team.aliens.dms_android.network.datasource

import team.aliens.data.datasource.remote.RemotePointDataSource
import team.aliens.domain.model.point.FetchPointsInput
import team.aliens.domain.model.point.FetchPointsOutput
import team.aliens.network.model.point.toDomain
import team.aliens.network.apiservice.PointApiService
import team.aliens.network.util.sendHttpRequest
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
