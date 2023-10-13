package team.aliens.dms.android.network.datasource

import team.aliens.dms.android.data.datasource.remote.RemotePointDataSource
import team.aliens.dms.android.network.apiservice.PointApiService
import team.aliens.dms.android.network.model.point.toDomain
import team.aliens.dms.android.network.util.sendHttpRequest
import team.aliens.dms.android.domain.model.point.FetchPointsInput
import team.aliens.dms.android.domain.model.point.FetchPointsOutput
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
