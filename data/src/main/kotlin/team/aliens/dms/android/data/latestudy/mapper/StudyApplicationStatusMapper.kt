package team.aliens.dms.android.data.latestudy.mapper

import team.aliens.dms.android.data.latestudy.model.StudyApplicationStatus
import team.aliens.dms.android.network.latestudy.model.StudyApplicationStatusResponse

fun StudyApplicationStatusResponse.toModel(): StudyApplicationStatus =
    StudyApplicationStatus(
        status = status,
        startDate = startDate,
        endDate = endDate,
    )
