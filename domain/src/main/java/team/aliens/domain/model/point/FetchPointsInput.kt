package team.aliens.domain.model.point

import team.aliens.domain.model._common.PointType

data class FetchPointsInput(
    val type: PointType,
    val page: Long? = null,
    val size: Long? = null,
)
