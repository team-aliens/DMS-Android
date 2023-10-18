package team.aliens.dms.android.domain._legacy.model.point

import team.aliens.dms.android.domain.model._common.PointType

data class FetchPointsInput(
    val type: PointType,
    val page: Long? = null,
    val size: Long? = null,
)
