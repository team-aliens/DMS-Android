package team.aliens.domain._model.point

import team.aliens.domain._model._common.PointType

data class FetchPointsInput(
    val type: PointType,
    val page: Long? = null,
    val size: Long? = null,
)
