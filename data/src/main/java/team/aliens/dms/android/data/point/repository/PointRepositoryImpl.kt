package team.aliens.dms.android.data.point.repository

import team.aliens.dms.android.data.point.model.FetchPointsOutput
import team.aliens.dms.android.network.point.datasource.NetworkPointDataSource
import team.aliens.dms.android.data.point.model.PointType
import javax.inject.Inject

internal class PointRepositoryImpl @Inject constructor(
    private val networkPointDataSource: NetworkPointDataSource,
) : PointRepository() {
    override fun fetchPoints(
        type: PointType,
        page: Long?,
        size: Long?,
    ): FetchPointsOutput {
        TODO("Not yet implemented")
    }
}
