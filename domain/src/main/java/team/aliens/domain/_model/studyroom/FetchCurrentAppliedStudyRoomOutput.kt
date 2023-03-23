package team.aliens.domain._model.studyroom

/**
 * A response returned when fetching current applied study room
 * @property floor the floor of the applied study room
 * @property name the name of the applied study room
 */
data class FetchCurrentAppliedStudyRoomOutput(
    val floor: Int,
    val name: String,
)
