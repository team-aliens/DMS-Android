package team.aliens.domain._model.studyroom

/**
 * A response returned when querying
 * @property floor the floor of the applied study room
 * @property name the name of the applied study room
 */
data class QueryCurrentAppliedStudyRoomResult(
    val floor: Int,
    val name: String,
)
