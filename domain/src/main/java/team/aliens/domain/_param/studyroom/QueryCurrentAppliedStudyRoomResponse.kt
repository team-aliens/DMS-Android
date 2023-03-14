package team.aliens.domain._param.studyroom

/**
 * @author junsuPark
 * A response returned when querying
 * @property floor the floor of the applied study room
 * @property name the name of the applied study room
 */
data class QueryCurrentAppliedStudyRoomResponse(
    val floor: Int,
    val name: String,
)
