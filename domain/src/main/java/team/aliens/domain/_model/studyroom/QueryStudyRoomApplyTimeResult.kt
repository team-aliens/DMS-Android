package team.aliens.domain._model.studyroom

/**
 * @author junsuPark
 * A response returned when querying study room apply time
 * @property startAt the start time of applying study room
 * @property endAt the end time of applying study room
 */
data class QueryStudyRoomApplyTimeResult(
    val startAt: String,
    val endAt: String,
)
