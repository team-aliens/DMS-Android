package team.aliens.dms.android.network.point.datasource

import team.aliens.dms.android.core.network.util.sendHttpRequest
import team.aliens.dms.android.network.point.apiservice.PointApiService
import team.aliens.dms.android.network.point.model.FetchPointsResponse
import javax.inject.Inject

internal class NetworkPointDataSourceImpl @Inject constructor(
    private val pointApiService: PointApiService,
) : NetworkPointDataSource() {

    override suspend fun fetchPoints(
        type: String,
        page: Long?,
        size: Long?,
    ): FetchPointsResponse = sendHttpRequest { pointApiService.fetchPoints(type, page, size) }
}