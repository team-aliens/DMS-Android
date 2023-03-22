package team.aliens.domain._model.studyroom

/**
 * A response returned when querying study room application time
 * @property startAt the start time of applying study room
 * @property endAt the end time of applying study room
 */
data class QueryStudyRoomApplicationTimeResult(
    val startAt: String,
    val endAt: String,
)
