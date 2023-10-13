package team.aliens.dms.android.domain.model.point

import java.util.UUID
import team.aliens.dms.android.domain.model._common.PointType

/**
 * A response returned when fetching points
 * @property totalPoint total points, summary of bonus and minus points
 * @property points list of point information
 */
data class FetchPointsOutput(
    val totalPoint: Int,
    val points: List<PointInformation>,
) {

    /**
     * A set of point information
     * @property id the point's unique id
     * @property date date of the specific point
     * @property type the type of point
     * @property name the name of point
     * @property score the score of point
     */
    data class PointInformation(
        val id: UUID,
        val date: String,
        val type: PointType,
        val name: String,
        val score: Int,
    )
}

fun FetchPointsOutput.PointInformation.toModel(): Point {
    return Point(
        id = this.id,
        date = this.date,
        type = this.type,
        name = this.name,
        score = this.score,
    )
}

fun List<FetchPointsOutput.PointInformation>.toModel(): List<Point> {
    return this.map(FetchPointsOutput.PointInformation::toModel)
}
