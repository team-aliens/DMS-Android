package team.aliens.dms.android.data.studyroom.model

import java.util.UUID

data class Timeslot(
    val id: UUID,
    val startTime: String,
    val endTime: String,
)
