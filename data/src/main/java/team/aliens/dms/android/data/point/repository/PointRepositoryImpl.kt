package team.aliens.dms.android.data.point.repository

import team.aliens.dms.android.data.point.mapper.toModel
import team.aliens.dms.android.data.point.model.PointStatus
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.network.point.datasource.NetworkPointDataSource
import javax.inject.Inject

internal class PointRepositoryImpl @Inject constructor(
    private val networkPointDataSource: NetworkPointDataSource,
) : PointRepository() {
    override suspend fun fetchPoints(
        type: PointType,
        page: Long?,
        size: Long?,
    ): PointStatus = networkPointDataSource.fetchPoints(
        type = type.name,
        page = page,
        size = size,
    ).toModel()
}
