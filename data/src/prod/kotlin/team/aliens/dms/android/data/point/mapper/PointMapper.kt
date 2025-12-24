package team.aliens.dms.android.data.point.mapper

import team.aliens.dms.android.data.point.model.Point
import team.aliens.dms.android.data.point.model.PointStatus
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.network.point.model.FetchPointsResponse
import team.aliens.dms.android.shared.date.toLocalDate

internal fun FetchPointsResponse.toModel(): PointStatus = PointStatus(
    totalPoints = this.totalPoint,
    points = this.pointResponses.toModel(),
)

private fun List<FetchPointsResponse.PointResponse>.toModel(): List<Point> =
    this.map(FetchPointsResponse.PointResponse::toModel)

private fun FetchPointsResponse.PointResponse.toModel(): Point = Point(
    id = this.id,
    date = this.date.toLocalDate(),
    type = PointType.valueOf(this.type),
    name = this.name,
    score = this.score,
)
