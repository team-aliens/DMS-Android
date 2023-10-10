package team.aliens.dms_android.domain.model.studyroom

/**
 * A response returned when fetching study room application time
 * @property startAt the start time of applying study room
 * @property endAt the end time of applying study room
 */
data class FetchStudyRoomApplicationTimeOutput(
    val startAt: String,
    val endAt: String,
)