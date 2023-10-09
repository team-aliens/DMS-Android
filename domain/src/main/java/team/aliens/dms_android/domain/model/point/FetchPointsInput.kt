package team.aliens.dms_android.domain.model.point

import team.aliens.dms_android.domain.model._common.PointType

data class FetchPointsInput(
    val type: PointType,
    val page: Long? = null,
    val size: Long? = null,
)
